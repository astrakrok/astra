import {useState} from "react";
import Button from "../../Button/Button";
import InfoHeader from "../../InfoHeader/InfoHeader";
import Input from "../../Input/Input";
import Textarea from "../../Textarea/Textarea";
import Badge from "../../Badge/Badge";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import SelectSubject from "../../SelectSubject/SelectSubject";
import {defaultEmptyTest} from "../../../data/default/test";
import withSubjectsDetails from "../../hoc/withSubjectsDetails/withSubjectsDetails";
import "./TestForm.css";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";

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
            variant.isCorrect = false;
        }
        test.variants[index].isCorrect = true;
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

    const renderVariant = (variant, index) => {
        return (
            <li className={`collection-item ${variant.isCorrect ? "correct" : ""}`} key={index}>
                <div className="s-vflex">
                    <div className="content">
                        <Input value={variant.title} placeholder="Варіант"
                               onChange={event => updateVariant({...variant, title: event.target.value}, index)}/>
                        <Spacer height={20}/>
                        <Textarea placeholder="Пояснення" value={variant.explanation} onChange={event => updateVariant({
                            ...variant,
                            explanation: event.target.value
                        }, index)}/>
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

    const addEmptyVariant = () => {
        setTest({
            ...test,
            variants: [
                ...test.variants,
                {
                    id: null,
                    title: "",
                    explanation: "",
                    isCorrect: false
                }
            ]
        })
    }

    const handleSubjectSaving = (subject, setPopupState) => {
        const subjects = test.subjects.filter(item => item.id !== subject.id);
        setTest({
            ...test,
            subjects: [...subjects, subject]
        });

        setPopupState();
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
            <InfoHeader>Основна інформація</InfoHeader>
            <Textarea placeholder="Питання" value={test.question}
                      onChange={event => setTest({...test, question: event.target.value})}/>
            <Textarea placeholder="Коментар" value={test.comment}
                      onChange={event => setTest({...test, comment: event.target.value})}/>
            <InfoHeader>Відповіді</InfoHeader>
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
            <InfoHeader>Предмети</InfoHeader>
            <div className="subjects s-hflex">
                {
                    test.subjects.map(renderSubject)
                }
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <Badge type="gray" wrapperClassName="add-subject-badge clickable"
                                   onClick={() => openSelectSubjectPopup(setPopupState)}>
                                <div className="s-vflex-center add-subject full-height">
                                    <i className="tiny material-icons">add</i>
                                </div>
                            </Badge>
                        )
                    }
                </PopupConsumer>
            </div>
            <div className="s-hflex-end">
                <LoaderBoundary condition={loading} size="small">
                    <Button onClick={save}>Зберегти</Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default TestForm;
