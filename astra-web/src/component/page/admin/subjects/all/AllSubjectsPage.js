import {useEffect, useState} from "react";
import {getSubjectsDetails} from "../../../../../service/subject.service";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import InfoText from "../../../../InfoText/InfoText";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import SubjectListItem from "../../../../SubjectListItem/SubjectListItem";
import Button from "../../../../Button/Button";
import Spacer from "../../../../Spacer/Spacer";
import SubjectForm from "../../../../form/SubjectForm/SubjectForm";
import "./AllSubjectsPage.css";
import withTitle from "../../../../hoc/withTitle/withTitle";
import {defaultSubject} from "../../../../../data/default/subject";

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

    const subjectForm = (setPopupState, subject) => {
        return (
            <SubjectForm onSuccess={() => onSubjectSaved(setPopupState)} subject={subject} />
        );
    }

    const openPopup = (setPopupState, subject = defaultSubject) => {
        setPopupState({
            bodyGetter: () => subjectForm(setPopupState, subject)
        });
    }

    const renderSubjectItem = item => (
        <PopupConsumer key={item.id}>
            {
                ({setPopupState}) => (
                    <SubjectListItem subject={item} onUpdateClick={() => openPopup(setPopupState, item)} />
                )
            }
        </PopupConsumer>
    )

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
                                subjects.map(renderSubjectItem)
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

export default withTitle(AllSubjectsPage, "Предмети");
