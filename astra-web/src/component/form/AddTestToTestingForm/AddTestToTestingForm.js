import {useEffect, useState} from "react";
import {getAvailableTests} from "../../../service/testing.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import FormError from "../../FormError/FormError";
import SingleSelect from "../../SingleSelect/SingleSelect";
import "./AddTestToTestingForm.css";
import Button from "../../Button/Button";

const AddTestToTestingForm = ({
                                  testingId,
                                  onSelected = () => {
                                  }
                              }) => {
    const [tests, setTests] = useState(null);
    const [selected, setSelected] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchTests = async () => {
            const tests = await getAvailableTests(testingId);
            setTests(tests);
        }

        fetchTests();
    }, []);

    const mapTestsToOptions = () => tests.map(item => ({
        label: item.question,
        value: item.id
    }));

    const updateSelected = option => {
        const selectedTest = tests.find(item => item.id === option.value);
        setSelected(selectedTest);
    }

    const confirmSelected = async () => {
        setLoading(true);
        await onSelected(selected);
        setLoading(false);
    }

    const renderTestVariant = (item, index) => (
        <div key={item.id} className="variant">
            <span className="order">{index + 1}.</span> {item.title}
        </div>
    );

    const renderForm = () => {
        return (
            <div className="s-vflex">
                <SingleSelect
                    placeholder="Виберіть тест"
                    onChange={updateSelected}
                    options={mapTestsToOptions()}/>
                {
                    selected ? (
                        selected.variants.map(renderTestVariant)
                    ) : (
                        <FormError className="select-test-error center"
                                   message="Виберіть тест для перегляду варіантів"/>
                    )
                }
                <div className="s-hflex-center">
                    <LoaderBoundary condition={loading} size="small">
                        <Button
                            isFilled={true}
                            onClick={confirmSelected}
                            disabled={selected == null}
                        >
                            Підтвердити
                        </Button>
                    </LoaderBoundary>
                </div>
            </div>
        );
    }

    return (
        <div className="AddTestToTestingForm">
            <LoaderBoundary condition={tests == null} className="s-hflex-center" size="small">
                {
                    tests && tests.length > 0 ? renderForm() : (
                        <FormError message="Немає доступних тестів" className="center weight-strong no-tests-error"/>
                    )
                }
            </LoaderBoundary>
        </div>
    );
}

export default AddTestToTestingForm;
