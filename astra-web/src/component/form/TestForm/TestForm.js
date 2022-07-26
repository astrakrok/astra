import {useState} from "react";
import Button from "../../Button/Button";
import InfoHeader from "../../InfoHeader/InfoHeader";
import Input from "../../Input/Input";
import Textarea from "../../Textarea/Textarea";
import Badge from "../../Badge/Badge";
import Spacer from "../../Spacer/Spacer";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import SelectExams from "../../popup-component/SelectExams/SelectExams";
import SelectSubject from "../../SelectSubject/SelectSubject";
import {defaultEmptyTest} from "../../../data/default/test";
import withSubjectsDetails from "../../hoc/withSubjectsDetails/withSubjectsDetails";
import withExams from "../../hoc/withExams/withExams";
import "./TestForm.css";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const TestForm = ({
    initialTest = defaultEmptyTest,
    onSend = () => {}
}) => {
    const [test, setTest] = useState(initialTest);
    const [loading, setLoading] = useState(false);

    const save = async () => {
        setLoading(true);
        await onSend(test);
        setLoading(false);
    }

    const updateVariant = (variant, index) => {
        test.variants[index] = variant;
        setTest({
            ...test
        });
    }

    const updateCorrectVariant = index => {
        for (const variant of test.variants) {
            variant.correct = false;
        }
        test.variants[index].correct = true;
        setTest({
            ...test
        });
    }

    const removeVariant = index => {
        const variants = test.variants.filter((_, itemIndex) => itemIndex !== index);
        setTest({
            ...test,
            variants
        });
    }

    const removeSubject = index => {
        const subjects = test.subjects.filter((_, itemIndex) => itemIndex !== index);
        setTest({
            ...test,
            subjects
        });
    }

    const removeExam = index => {
        const exams = test.exams.filter((_, itemIndex) => itemIndex !== index);
        setTest({
            ...test,
            exams
        });
    }

    const renderVariant = (variant, index) => {
        return (
            <li className={`collection-item ${variant.correct ? "correct" : ""}`} key={index}>
                <div className="s-vflex">
                    <div className="content">
                        <Input value={variant.title} placeholder="Варіант" onChange={event => updateVariant({...variant, title: event.target.value}, index)} />
                        <Textarea placeholder="Пояснення" value={variant.explanation}  onChange={event => updateVariant({...variant, explanation: event.target.value}, index)} />
                    </div>
                    <div className="s-hflex">
                        <div className="clickable equal-flex make-correct s-hflex-center" onClick={() => updateCorrectVariant(index)}>
                            <i className="material-icons">check</i>
                        </div>
                        <div className="clickable equal-flex delete-variant s-hflex-center" onClick={() => removeVariant(index)}>
                            <i className="material-icons">close</i>
                        </div>
                    </div>
                </div>
            </li>
        );
    }

    const renderSubject = (subject, index) => {
        return (
            <Badge key={subject.id} type="green">
                <div className="s-hflex">
                    <span className="long-text">{subject.title}</span>
                    <div className="delete-subject s-vflex-center" onClick={() => removeSubject(index)}>
                        <i className="tiny material-icons">close</i>
                    </div>
                </div>
            </Badge>
        );
    }

    const renderExam = (exam, index) => {
        return (
            <Badge key={exam.id} type="primary">
                <div className="s-hflex">
                    <span>{exam.title}</span>
                    <div className="delete-exam s-vflex-center"  onClick={() => removeExam(index)}>
                        <i className="tiny material-icons">close</i>
                    </div>
                </div>
            </Badge>
        );
    }

    const addEmptyVariant = () => {
        setTest({
            ...test,
            variants: [
                ...test.variants,
                {
                    id: null,
                    title: "",
                    explanation: "",
                    correct: false
                }
            ]
        })
    }

    const changeSelectedExams = selected => {
        setTest({
            ...test,
            exams: selected.map(item => ({id: item.value, title: item.label}))
        });
    }

    const handleExamSaving = (exam, setPopupState) => {
        setTest({
            ...test,
            exams: [...test.exams, exam]
        });

        setPopupState();
    }

    const handleSubjectSaving = (subject, setPopupState) => {
        const subjects = test.subjects.filter(item => item.id !== subject.id);
        setTest({
            ...test,
            subjects: [...subjects, subject]
        });

        setPopupState();
    }

    const openSelectExamsPopup = setPopupState => {
        const SelectExamsForm = withExams(SelectExams, "small");

        setPopupState({
            bodyGetter: () => (
                <SelectExamsForm
                    onChange={changeSelectedExams}
                    selectedExams={test.exams}
                    onSave={exam => handleExamSaving(exam, setPopupState)} />
            )
        });
    }

    const openSelectSubjectPopup = setPopupState => {
        const SelectSubjectForm = withSubjectsDetails(SelectSubject, "small");

        setPopupState({
            bodyGetter: () => (
                <div className="s-hflex-center">
                    <SelectSubjectForm onSave={subject => handleSubjectSaving(subject, setPopupState)} />
                </div>
            )
        });
    }

    return (
        <div className="TestForm s-vflex">
            <InfoHeader text="Основна інформація" />
            <Input placeholder="Питання" value={test.question} onChange={event => setTest({...test, question: event.target.value})} />
            <Textarea placeholder="Коментар" value={test.comment} onChange={event => setTest({...test, comment: event.target.value})} />
            <InfoHeader text="Відповіді" />
            <div className="answers s-vflex">
                <ul className="collection">
                    {
                        test.variants.map(renderVariant)
                    }
                    <li className="collection-item clickable add" onClick={addEmptyVariant}>
                        <div className="s-hflex-center">
                            <i className="medium material-icons">add</i>
                        </div>
                    </li>
                </ul>
            </div>
            <InfoHeader text="Предмети" />
            <div className="subjects s-hflex">
                {
                    test.subjects.map(renderSubject)
                }
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <Badge type="gray" wrapperClassName="add-subject-badge clickable" onClick={() => openSelectSubjectPopup(setPopupState)}>
                                <div className="s-vflex-center add-subject full-height">
                                    <i className="tiny material-icons">add</i>
                                </div>
                            </Badge>
                        )
                    }
                </PopupConsumer>
            </div>
            <InfoHeader text="Іспити" />
            <div className="exams s-hflex">
                {
                    test.exams.map(renderExam)
                }
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <Badge type="gray" wrapperClassName="add-exam-badge clickable" onClick={() => openSelectExamsPopup(setPopupState)}>
                                <div className="s-vflex-center add-exam full-height">
                                    <i className="tiny material-icons">add</i>
                                </div>
                            </Badge>
                        )
                    }
                </PopupConsumer>
            </div>
            <Spacer height={50} />
            <div className="s-hflex-end">
                <LoaderBoundary condition={loading} size="small">
                    <Button onClick={save}>Зберегти</Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default TestForm;
