import {useEffect, useState} from "react";
import {page} from "../../../constant/page";
import Ref from "../../Ref/Ref";
import "./ExamSpecializationsForm.css";
import {create, getExamTestings} from "../../../service/testing.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import {getAvailableSpecializations} from "../../../service/exam.service";
import Spacer from "../../Spacer/Spacer";
import Button from "../../Button/Button";
import SelectSpecializationForm from "../SelectSpecializationForm/SelectSpecializationForm";
import FormError from "../../FormError/FormError";

const ExamSpecializationsForm = ({exam}) => {
    const [testings, setTestings] = useState([]);
    const [testingsLoading, setTestingsLoading] = useState(false);
    const [availableSpecializations, setAvailableSpecializations] = useState(null);
    const [availableSpecializationsLoading, setAvailableSpecializationsLoading] = useState(false);

    const fetchTestings = async () => {
        setTestingsLoading(true);
        const testings = await getExamTestings(exam.id);
        setTestingsLoading(false);
        setTestings(testings);
    }

    const fetchAvailableSpecializations = async () => {
        setAvailableSpecializationsLoading(true);
        const availableSpecializations = await getAvailableSpecializations(exam.id);
        setAvailableSpecializationsLoading(false);
        setAvailableSpecializations(availableSpecializations);
    }

    useEffect(() => {
        fetchTestings();
    }, []);

    const renderTestingItem = item => (
        <div key={item.id} className="item">
            <Ref
                className="center auth-link s-vflex-center"
                to={page.admin.testings.id(item.id).edit}
            >
                {item.specialization.title}
            </Ref>
        </div>
    );

    const createTesting = async specializationId => {
        const data = {
            examId: exam.id,
            specializationId
        };
        setAvailableSpecializations(null);
        await create(data);
        await fetchTestings();
    }

    return (
        <div className="ExamSpecializationsForm">
            <div className="testings">
                <LoaderBoundary condition={testingsLoading} size="small" className="s-hflex-center">
                    {
                        testings.length === 0 ? (
                            <FormError message="Ви ще не додали жодної спеціалізації"
                                       className="error weight-strong center"/>
                        ) : (
                            testings.map(renderTestingItem)
                        )
                    }
                </LoaderBoundary>
            </div>
            <Spacer height={30}/>
            <div className="control s-hflex-center">
                <LoaderBoundary condition={availableSpecializationsLoading} size="small">
                    {
                        availableSpecializations == null ? (
                            <Button isFilled={true} onClick={fetchAvailableSpecializations}>
                                Додати спеціалізацію
                            </Button>
                        ) : (
                            availableSpecializations.length === 0 ? (
                                <FormError message="Немає доступних спеціалізацій"/>
                            ) : (
                                <SelectSpecializationForm
                                    specializations={availableSpecializations}
                                    onSelect={createTesting}/>
                            )
                        )
                    }
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default ExamSpecializationsForm;
