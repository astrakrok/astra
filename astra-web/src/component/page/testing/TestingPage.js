import {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {getTraining} from "../../../service/test.service";
import {start} from "../../../service/examination.service";
import "./TestingPage.css";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import TrainingTesting from "../../TrainingTesting/TrainingTesting";
import ExaminationTesting from "../../ExaminationTesting/ExaminationTesting";
import InfoText from "../../InfoText/InfoText";

const TestingPage = () => {
    const [searchParams] = useSearchParams();

    const [Testing, setTesting] = useState(null);

    const getOptionsFromSearchParams = () => {
        const options = {
            testingId: searchParams.get("testingId"),
            mode: searchParams.get("mode")
        };
        const count = searchParams.get("count");
        if (count) {
            options.count = count;
        }
        return options;
    }

    const getTesting = async options => {
        if (options.mode === "training") {
            const result = await getTraining(options);
            setTesting(<TrainingTesting tests={result}/>);
        } else if (options.mode === "examination") {
            const result = await start(options);
            setTesting(
                <ExaminationTesting
                    id={result.id}
                    tests={result.tests}
                    finishedAt={result.finishedAt}
                    onRefresh={() => getTesting(options)}/>)
        } else {
            setTesting(
                <InfoText>
                    Режим не доступний
                </InfoText>
            );
        }
    }

    useEffect(() => {
        const options = getOptionsFromSearchParams();
        getTesting(options);
    }, []);

    return (
        <div className="container TestingPage">
            <div className="row">
                <LoaderBoundary condition={Testing == null} className="s-hflex-center">
                    {
                        Testing ? Testing : null
                    }
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default TestingPage;
