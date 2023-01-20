import {useState} from "react";
import {getAvailableTests} from "../../../service/testing.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import FormError from "../../FormError/FormError";
import SingleSelect from "../../SingleSelect/SingleSelect";
import "./AddTestToTestingForm.css";
import Button from "../../Button/Button";
import {throttle} from "../../../handler/common.handler";

const load = throttle(async (testingId, value, setTests) => {
    if (!value || value.length < 4) {
        return;
    }
    const result = await getAvailableTests(testingId, {searchText: value}, {pageSize: 30, pageNumber: 0});
    setTests(result.items);
}, 2000);

const AddTestToTestingForm = ({
                                  testingId,
                                  onSelected = () => {
                                  }
                              }) => {
    const [tests, setTests] = useState([]);
    const [selected, setSelected] = useState(null);
    const [loading, setLoading] = useState(false);
    
    const loadOptions = value => {
        load(testingId, value, setTests);
    }

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

    return (
        <div className="AddTestToTestingForm">
            <div className="s-vflex">
                <SingleSelect
                    placeholder="Введіть щонайменше 4 символи для пошуку"
                    onInputChange={loadOptions}
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
        </div>
    );
}

export default AddTestToTestingForm;
