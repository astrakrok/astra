import {useEffect, useState} from "react";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import Button from "../../../../Button/Button";
import InfoText from "../../../../InfoText/InfoText";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../../../Spacer/Spacer";
import {getAll} from "../../../../../service/exam.service";
import "./AllExamsPage.css";
import CreateExamForm from "../../../../form/CreateExamForm/CreateExamForm";
import ExamItem from "../../../../ExamItem/ExamItem";
import withTitle from "../../../../hoc/withTitle/withTitle";

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
            bodyGetter: () => <CreateExamForm onSuccess={() => examSaved(setPopupState)} />
        });
    }

    const renderExamItem = exam => {
        return (
            <div key={exam.id} className="col xs12 s6 m4 l3">
                <ExamItem exam={exam} />
            </div>
        );
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
                    <div className="exams-list">
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
