import {useEffect, useState} from "react";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import Button from "../../../../Button/Button";
import InfoText from "../../../../InfoText/InfoText";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../../../Spacer/Spacer";
import {deleteExam, getAll} from "../../../../../service/exam.service";
import "./AllExamsPage.css";
import ExamForm from "../../../../form/ExamForm/ExamForm";
import ExamItem from "../../../../ExamItem/ExamItem";
import withTitle from "../../../../hoc/withTitle/withTitle";
import ActionDialog from "../../../../popup-component/ActionDialog/ActionDialog";

const AllExamsPage = () => {
    const [exams, setExams] = useState(null);

    const fetchExams = async () => {
        const result = await getAll();
        setExams(result);
    }

    useEffect(() => {
        fetchExams();
    }, []);

    const examSaved = setPopupState => {
        setPopupState();
        fetchExams();
    }

    const openCreateExamPopup = setPopupState => {
        setPopupState({
            bodyGetter: () => <ExamForm onSuccess={() => examSaved(setPopupState)} />
        });
    }

    const onDeletionConfirmed = async examId => {
        await deleteExam(examId);
        fetchExams();
    }

    const renderExamItem = exam => {
        return (
            <div key={exam.id} className="col xs12 s6 m4 l3">
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <ExamItem exam={exam} onDeleteClick={() => showExamDeletionWarning(setPopupState, exam.id)} />
                        )
                    }
                </PopupConsumer>
            </div>
        );
    }

    const showExamDeletionWarning = (setPopupState, examId) => {
        setPopupState({
            bodyGetter: () => <ActionDialog
                message="Ви впевнені, що хочете видалити цей іспит? Його вже неможливо буде повернути"
                setPopupState={setPopupState}
                onConfirm={async () => await onDeletionConfirmed(examId)} />
        })
    }

    return (
        <div className="AllExamsPage container">
            <div className="row">
                <div className="s-vflex">
                    <div className="col s-hflex-end">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <Button isFilled={true} onClick={() => openCreateExamPopup(setPopupState)}>
                                        Створити
                                    </Button>
                                )
                            }
                        </PopupConsumer>
                    </div>
                    <Spacer height={20} />
                    <div className="exams-list center">
                        <LoaderBoundary condition={exams == null}>
                            {
                                (exams && exams.length > 0) ? (
                                    exams.map(renderExamItem)
                                ) : (
                                    <InfoText>
                                        Іспити відсутні
                                    </InfoText>
                                )
                            }
                        </LoaderBoundary>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default withTitle(AllExamsPage, "Іспити");
