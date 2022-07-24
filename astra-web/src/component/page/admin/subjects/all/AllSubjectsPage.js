import {useEffect, useState} from "react";
import {getSubjectsDetails} from "../../../../../service/subject.service";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import InfoText from "../../../../InfoText/InfoText";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import SubjectListItem from "../../../../SubjectListItem/SubjectListItem";
import Button from "../../../../Button/Button";
import Spacer from "../../../../Spacer/Spacer";
import CreateSubjectForm from "../../../../form/CreateSubjectForm/CreateSubjectForm";
import "./AllSubjectsPage.css";

const AllSubjectsPage = () => {
    const [subjects, setSubjects] = useState(null);

    const fetchSubjects = async () => {
        setSubjects(null);
        const subjects = await getSubjectsDetails();
        setSubjects(subjects);
    }

    useEffect(() => {
        fetchSubjects();
    }, []);

    const onSubjectSaved = setPopupState => {
        setPopupState();
        fetchSubjects();
    }

    const createSubjectForm = setPopupState => {
        return (
            <CreateSubjectForm onSuccess={() => onSubjectSaved(setPopupState)} />
        );
    }

    const openPopup = setPopupState => {
        setPopupState({
            bodyGetter: () => createSubjectForm(setPopupState)
        });
    }

    return (
        <div className="AllSubjectsPage container">
            <div className="row">
                <div className="s-hflex-end">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={() => openPopup(setPopupState)}>
                                    Створити
                                </Button>
                            )
                        }
                    </PopupConsumer>
                </div>
                <Spacer height={15}/>
                <div className="center">
                    <LoaderBoundary condition={subjects == null}>
                        {
                            (subjects && subjects.length > 0) ? (
                                subjects.map(item => <SubjectListItem key={item.id} subject={item}/>)
                            ) : (
                                <InfoText>
                                    Предмети відсутні
                                </InfoText>
                            )
                        }
                    </LoaderBoundary>
                </div>
            </div>
        </div>
    );
}

export default AllSubjectsPage;
