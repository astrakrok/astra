import {useEffect, useState} from "react";
import {getTraining} from "../../../service/test.service";
import SelectTestingOptionsForm from "../../form/SelectTestingOptionsForm/SelectTestingOptionsForm";
import withSpecializationsAndExams from "../../hoc/withSpecializationsAndExams/withSpecializationsAndExams";
import withTitle from "../../hoc/withTitle/withTitle";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import TrainingTesting from "../../TrainingTesting/TrainingTesting";
import InfoText from "../../InfoText/InfoText";
import {useSearchParams} from "react-router-dom";
import ExaminationTesting from "../../ExaminationTesting/ExaminationTesting";
import withAllAvailableHeight from "../../hoc/withAllAvailableHeight/withAllAvailableHeight";
import "./TestingPage.css";
import {start} from "../../../service/examination.service";

const TestingPage = () => {
    const [searchParams, setSearchParams] = useSearchParams();

    const [testing, setTesting] = useState(null);
    const [loading, setLoading] = useState(false);

    const fetchTesting = async options => {
        setLoading(true);
        const result = options.mode === "training" ? (
            await getTraining(options)
        ) : (
            await start(options)
        );
        setSearchParams(options);
        setTesting(result);
        setLoading(false);
    }

    const getOptionsFromSearchParams = () => {
        const options = {
            examId: searchParams.get("examId"),
            specializationId: searchParams.get("specializationId"),
            mode: searchParams.get("mode")
        };
        const count = searchParams.get("count");
        if (count) {
            options.count = count;
        }
        return options;
    }

    const isValidOptions = options => {
        if (!options.examId || !options.specializationId || !options.mode) {
            return false;
        }
        return options.mode === "examination" || (options.mode === "training" && options.count != null);
    }

    useEffect(() => {
        setTesting(null);
        const options = getOptionsFromSearchParams();
        if (!isValidOptions(options)) {
            return;
        }

        fetchTesting(options);
    }, [searchParams]);

    const Form = withSpecializationsAndExams(SelectTestingOptionsForm);

    const displayTesting = () => {
        const options = getOptionsFromSearchParams();
        switch (options.mode) {
            case "training":
                return <TrainingTesting tests={testing} />
            case "examination":
                return <ExaminationTesting
                    id={testing.id}
                    tests={testing.tests}
                    finishedAt={testing.finishedAt}
                    onRefresh={() => fetchTesting(options)} />
            default:
                return (
                    <InfoText>
                        Режим не доступний
                    </InfoText>
                );
        }
    }

    return (
        <div className="container">
            <div className="row">
                <div className="s-hflex-center">
                    {
                        (testing == null || searchParams.get("mode") == null) ? (
                            <LoaderBoundary condition={loading}>
                                <Form initialOptions={getOptionsFromSearchParams()} onSelect={setSearchParams} />
                            </LoaderBoundary>
                        ) : (
                            displayTesting()
                        )
                    }
                </div>
            </div>
        </div>
    );
}

export default withAllAvailableHeight(withTitle(TestingPage, "Тестування"));
