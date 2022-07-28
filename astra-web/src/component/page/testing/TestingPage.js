import {useEffect, useState} from "react";
import {getTestsForTesting} from "../../../service/test.service";
import SelectTestingOptionsForm from "../../form/SelectTestingOptionsForm/SelectTestingOptionsForm";
import withSpecializationsAndExams from "../../hoc/withSpecializationsAndExams/withSpecializationsAndExams";
import withTitle from "../../hoc/withTitle/withTitle";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import TrainingTesting from "../../TrainingTesting/TrainingTesting";
import InfoText from "../../InfoText/InfoText";
import "./TestingPage.css";
import {useSearchParams} from "react-router-dom";

const TestingPage = () => {
    const [searchParams, setSearchParams] = useSearchParams();

    const [tests, setTests] = useState(null);
    const [loading, setLoading] = useState(false);
    const [options, setOptions] = useState({});

    useEffect(() => {
        setOptions({
            examId: searchParams.get("examId"),
            specializationId: searchParams.get("specializationId"),
            mode: searchParams.get("mode"),
            count: searchParams.get("count")
        });
    }, [searchParams]);

    useEffect(() => {
        if (!options.mode || !options.examId || !options.specializationId || !options.count) {
            return;
        }

        const fetchTests = async () => {
            setLoading(true);
            const result = await getTestsForTesting(options);
            setSearchParams(options);
            setTests(result);
            setLoading(false);
        }

        fetchTests();
    }, [options]);

    const Form = withSpecializationsAndExams(SelectTestingOptionsForm);

    const displayTesting = () => {
        switch (options.mode) {
            case "training":
                return <TrainingTesting tests={tests} />
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
                        (tests == null || options.mode == null) ? (
                            <LoaderBoundary condition={loading}>
                                <Form initialOptions={options} onSelect={setOptions} />
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

export default withTitle(TestingPage, "Тестування");
