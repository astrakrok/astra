import React, {useState} from "react";
import {Link} from "react-router-dom";
import {page} from "../../../../constant/page";
import {compareTitles} from "../../../../handler/sort.handler";
import {mapTestingStatusToTest} from "../../../../mapper/enum.mapper";
import {mapToGroups} from "../../../../mapper/testing.mapper";
import {getFullDetailedTest} from "../../../../service/test.service";
import Button from "../../../Button/Button";
import DisplayBoundary from "../../../DisplayBoundary/DisplayBoundary";
import LoaderBoundary from "../../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../../Spacer/Spacer";
import Table from "../../../Table/Table";
import Tooltipped from "../../../Tooltipped/Tooltipped";
import "./TestingsList.css";

const TestingsList = ({testId, initialTestings}) => {
    const [refreshing, setRefreshing] = useState(false);
    const [testings, setTestings] = useState(initialTestings);

    const refreshTestingsList = async () => {
        setRefreshing(true);
        const test = await getFullDetailedTest(testId);
        setRefreshing(false);
        setTestings(test.testings);
    }

    return (
        <div className="s-vflex TestingsList">
            <div className="s-hflex">
                <div className="refresh">
                    <LoaderBoundary condition={refreshing} size="small">
                        <Tooltipped tooltip="Оновити" position="top">
                            <i className="material-icons clickable delete small"
                               onClick={refreshTestingsList}>refresh</i>
                        </Tooltipped>
                    </LoaderBoundary>
                </div>
                <div className="equal-flex"/>
                <div>
                    <Button isFilled={true}>
                        Додати іспит
                    </Button>
                </div>
            </div>
            <Spacer height={10}/>
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
                    mapToGroups(testings).sort(compareTitles).map(item => (
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
                                                    <td>
                                                        <DisplayBoundary condition={specialization.status === "DRAFT"}>
                                                            <div className="s-hflex-end">
                                                                <LoaderBoundary condition={false} size="small">
                                                                    <Tooltipped tooltip="Видалити" position="top">
                                                                        <i className="material-icons clickable delete">close</i>
                                                                    </Tooltipped>
                                                                </LoaderBoundary>
                                                            </div>
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
        </div>
    );
}

export default TestingsList;
