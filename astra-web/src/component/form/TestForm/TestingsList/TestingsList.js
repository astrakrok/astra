import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {page} from "../../../../constant/page";
import PopupConsumer from "../../../../context/popup/PopupConsumer";
import {compareTitles} from "../../../../handler/sort.handler";
import {mapTestingStatusToTest} from "../../../../mapper/enum.mapper";
import {mapToGroups} from "../../../../mapper/testing.mapper";
import {getTestings, saveByDetails} from "../../../../service/testing.test.service";
import Button from "../../../Button/Button";
import DisplayBoundary from "../../../DisplayBoundary/DisplayBoundary";
import LoaderBoundary from "../../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../../Spacer/Spacer";
import Table from "../../../Table/Table";
import Tooltipped from "../../../Tooltipped/Tooltipped";
import "./TestingsList.css";
import DeleteRelation from "../DeleteRelation/DeleteRelation";
import {mapToErrors} from "../../../../mapper/error.testing.test.mapper";
import ErrorsArea from "../../../ErrorsArea/ErrorsArea";
import SelectTestingForm from "../../SelectTestingForm/SelectTestingForm";

const TestingsList = ({testId}) => {
    const [testings, setTestings] = useState(null);
    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);

    const refreshTestingsList = async () => {
        setTestings(null);
        setErrors({});
        const result = await getTestings(testId);
        setTestings(result);
    }

    useEffect(() => {
        refreshTestingsList();
    }, []);

    const processResponse = result => {
        if (Array.isArray(result)) {
            const errors = mapToErrors(result);
            setErrors(errors);
            return;
        }
        refreshTestingsList();
    }

    const testingSelected = async (testing, setPopupState) => {
        setPopupState();
        const details = {testId: testId, examId: testing.exam.id, specializationId: testing.specialization.id};
        setLoading(true);
        const result = await saveByDetails(details);
        if (result.id) {
            refreshTestingsList();
        } else {
            setErrors(mapToErrors(result));
        }
        setLoading(false);
    }

    const openSelectTestingForm = setPopupState => {
        setPopupState({
            size: "medium",
            bodyGetter: () => <SelectTestingForm testId={testId} onTestingSelected={testing => testingSelected(testing, setPopupState)} />
        });
    }

    return (
        <div className="s-vflex TestingsList">
            <div className="s-hflex">
                <div className="refresh">
                    <DisplayBoundary condition={testings != null} size="small">
                        <Tooltipped tooltip="Оновити" position="top">
                            <i className="material-icons clickable delete small"
                               onClick={refreshTestingsList}>refresh</i>
                        </Tooltipped>
                    </DisplayBoundary>
                </div>
                <div className="equal-flex"/>
                <div>
                    <LoaderBoundary condition={loading} size="small">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <Button isFilled={true} onClick={() => openSelectTestingForm(setPopupState)}>
                                        Додати іспит
                                    </Button>
                                )
                            }
                        </PopupConsumer>
                    </LoaderBoundary>
                </div>
            </div>
            <DisplayBoundary condition={Object.keys(errors).length > 0}>
                <Spacer height={20} />
                <ErrorsArea errors={errors.status} />
                <ErrorsArea errors={errors.conflict} />
            </DisplayBoundary>
            <Spacer height={10} />
            <LoaderBoundary condition={testings == null} className="full-width center">
                <Table type="secondary">
                    <thead>
                    <tr>
                        <th>КРОК</th>
                        <th>Спеціалізація</th>
                        <th>Статус</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        (!testings || testings.length === 0) ? (
                            <tr><td colSpan={5} className="center">Ще немає іспитів</td></tr>
                        ) : mapToGroups(testings).sort(compareTitles).map(item => (
                            <React.Fragment key={item.id}>
                                <tr key={item.id} className="center">
                                    <td colSpan={5} className="center weight-strong">{item.title}</td>
                                </tr>
                                {
                                    item.steps.sort(compareTitles).map(stepGroup => (
                                        <React.Fragment key={stepGroup.id}>
                                            {
                                                stepGroup.specializations.sort(compareTitles).map((specialization, index) => (
                                                    <tr key={stepGroup.id + "-" + specialization.id}>
                                                        <DisplayBoundary condition={index === 0}>
                                                            <td className="step-title"
                                                                rowSpan={stepGroup.specializations.length}>{stepGroup.title}</td>
                                                        </DisplayBoundary>
                                                        <td className="full-width">{specialization.title}</td>
                                                        <td className={specialization.status.toLowerCase() + " weight-strong"}>{mapTestingStatusToTest(specialization.status)}</td>
                                                        <td className="center">
                                                            <DisplayBoundary condition={specialization.status === 'DRAFT'}>
                                                                <DeleteRelation testingId={specialization.testingId} testId={testId} onDelete={processResponse} />
                                                            </DisplayBoundary>
                                                        </td>
                                                        <td>
                                                            <div className="s-hflex-end">
                                                                <LoaderBoundary condition={false} size="small">
                                                                    <Link
                                                                        to={page.admin.testings.id(specialization.testingId).edit}
                                                                        target="_blank" className="view">
                                                                        <Tooltipped tooltip="Переглянути" position="top">
                                                                            <i className="material-icons clickable">visibility</i>
                                                                        </Tooltipped>
                                                                    </Link>
                                                                </LoaderBoundary>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                ))
                                            }
                                        </React.Fragment>
                                    ))
                                }
                            </React.Fragment>
                        ))
                    }
                    </tbody>
                </Table>
            </LoaderBoundary>
        </div>
    );
}

export default TestingsList;
