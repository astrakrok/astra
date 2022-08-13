import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {getTestingInfo, getTests} from "../../../../../service/testing.service";
import {deleteById} from "../../../../../service/testing.test.service";
import Button from "../../../../Button/Button";
import InfoHeader from "../../../../InfoHeader/InfoHeader";
import InfoText from "../../../../InfoText/InfoText";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../../../Spacer/Spacer";
import TestsQuestionsTable from "../../../../TestsQuestionsTable/TestsQuestionsTable";
import "./EditTestingPage.css";

const EditTestingPage = () => {
    const {id} = useParams();
    const [testingInfo, setTestingInfo] = useState(null);
    const [tests, setTests] = useState(null);

    const fetchTestingInfo = async () => {
        const testingInfo = await getTestingInfo(id);
        setTestingInfo(testingInfo);
    }

    const fetchTests = async () => {
        const tests = await getTests(id);
        setTests(tests);
    }

    useEffect(() => {
        fetchTestingInfo();
        fetchTests();
    }, []);

    const deleteTestingTest = async testingTestId => {
        const result = await deleteById(testingTestId);
        if (!result.error) {
            setTests(previous => previous.filter(item => item.id !== testingTestId))
        }
    }

    return (
        <div className="container">
            <div className="row">
                <div className="s-vflex">
                    <div className="create s-hflex-end">
                        <Button isFilled={true}>
                            Додати тест
                        </Button>
                    </div>
                    <Spacer height={20}/>
                    <div className="info">
                        <InfoHeader className="s-hflex full-width">
                            {
                                testingInfo ? (
                                    <div>{testingInfo.exam.title}({testingInfo.specialization.title})</div>
                                ) : null
                            }
                            <div className="equal-flex"/>
                            <div>Кількість тестів - {tests ? tests.length : 0}</div>
                        </InfoHeader>
                    </div>
                    <div className="tests">
                        <LoaderBoundary condition={tests == null} className="s-hflex-center">
                            {
                                tests && tests.length > 0 ? (
                                    <TestsQuestionsTable
                                        testingsTests={tests}
                                        onDelete={deleteTestingTest}/>
                                ) : (
                                    <InfoText className="s-hflex-center">Ви поки не додали тестів</InfoText>
                                )
                            }
                        </LoaderBoundary>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditTestingPage;
