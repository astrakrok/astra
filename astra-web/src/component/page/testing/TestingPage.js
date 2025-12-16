import {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {getAdaptive, getTraining} from "../../../service/test.service";
import {start} from "../../../service/examination.service";
import "./TestingPage.css";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import TrainingTesting from "../../TrainingTesting/TrainingTesting";
import ExaminationTesting from "../../ExaminationTesting/ExaminationTesting";
import InfoText from "../../InfoText/InfoText";
import withScrollTopButton from "../../hoc/withScrollTopButton/withScrollTopButton";

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
        const specializationId = searchParams.get("specializationId");
        if (specializationId) {
            options.specializationId = specializationId;
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
        } else if (options.mode === "adaptive") {
            const result = await getAdaptive(options);
            setTesting(<TrainingTesting tests={result} />);
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


export default withScrollTopButton(TestingPage);
