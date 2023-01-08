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
import Editor from "../../Editor/Editor";
import SvgContentChooser from "../../SvgContentChooser/SvgContentChooser";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";
import {validateTest} from "../../../validation/custom/test.validator";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import Alert from "../../Alert/Alert";

const TestForm = ({
                      initialTest = defaultEmptyTest,
                      onSend = () => {
                      }
                  }) => {
    const [test, setTest] = useState(initialTest);
    const [formState, setFormState] = useState({
        loading: false,
        errors: {}
    });

    const notFoundSubjects = test.importDetails.errors ? test.importDetails.errors
        .filter(error => error.type === "NOT_FOUND")
        .map(error => error.details.subject) : [];

    const duplicateSubjects = test.importDetails.errors ? test.importDetails.errors
        .filter(error => error.type === "CONFLICT")
        .map(error => error.details.subject) : [];

    const getVariantPropertyError = (index, property) => {
        if (!formState.errors.variants || !formState.errors.variants[index]) {
            return null;
        }
        return formState.errors.variants[index][property];
    }

    const save = async setPopupState => {
        setFormState({
            loading: true,
            errors: {}
        });
        const validationResult = validateTest(test);
        if (validationResult.hasError) {
            setFormState({
                loading: false,
                errors: validationResult.errors
            });
            setPopupState({
                bodyGetter: () => <MessagePopupBody message="Збереження не можливе. Тест містить помилки" />
            });
            return;
        }
        await onSend(test);
        setFormState({
            loading: false,
            errors: {}
        });
    }

    const saveDraft = async () => {
        setFormState({
            loading: true,
            errors: {}
        });
    }

    const updateVariant = (values, index) => {
        setTest(previous => {
            previous.variants[index] = {
                ...previous.variants[index],
                ...values
            };
            return {
                ...previous
            };
        });
    }

    const updateVariantSvg = (index, property, content) => {
        setTest(previous => {
            previous.variants[index] = {
                ...previous.variants[index],
                [property]: content
            };
            return {
                ...previous
            };
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
                               onChange={event => updateVariant({title: event.target.value}, index)}/>
                        <ErrorsArea errors={getVariantPropertyError(index, "title")}/>
                        <Spacer height={10}/>
                        <SvgContentChooser
                            value={variant.titleSvg}
                            setValue={content => updateVariantSvg(index, "titleSvg", content)}/>
                        <Spacer height={20}/>
                        <Editor
                            placeholder="Пояснення"
                            value={variant.explanation}
                            onChange={content => updateVariant({
                                explanation: content
                            }, index)}/>
                        <ErrorsArea errors={getVariantPropertyError(index, "explanation")}/>
                        <Spacer height={10}/>
                        <SvgContentChooser
                            value={variant.explanationSvg}
                            setValue={content => updateVariantSvg(index, "explanationSvg", content)}/>
                    </div>
                    <div className="s-hflex">
                        <div className="clickable equal-flex make-correct s-hflex-center"
                             onClick={() => updateCorrectVariant(index)}>
                            <i className="material-icons">check</i>
                        </div>
                        <div className="clickable equal-flex delete-variant s-hflex-center"
                             onClick={() => removeVariant(index)}>
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
                    titleSvg: null,
                    explanation: "",
                    explanationSvg: null,
                    isCorrect: false
                }
            ]
        })
    }

    const updateTestProperty = (property, value) => {
        setTest(previvous => ({
            ...previvous,
            [property]: value
        }));
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
                    <SelectSubjectForm onSave={subject => handleSubjectSaving(subject, setPopupState)}/>
                </div>
            )
        });
    }

    const updateTestComment = content => {
        setTest(previous => ({
            ...previous,
            comment: content
        }));
    }

    const renderDuplicateSubjectItem = (subject, index) => (
        <>
            <div>Предмет <strong>{subject.title}</strong> був знайденим у наступних спеціалізаціях: </div>
            <ul className="browser-default errors-list">
                {
                    subject.items.map(item => <li key={item.specializationId}>{item.specializationTitle} ({item.stepTitle})</li>)
                }
            </ul>
        </>
    );

    return (
        <div className="TestForm s-vflex">
            <InfoHeader>Основна інформація</InfoHeader>
            {
                (notFoundSubjects || duplicateSubjects) ? (
                    <>
                        <Alert type="warning">
                            При імпорті предметів для даного тесту виникли помилки.
                            {
                                notFoundSubjects.length > 0 ? (
                                    <>
                                        <div>Предмети, які не були знайденими:</div>
                                        <ul className="browser-default errors-list">
                                            {
                                                notFoundSubjects.map((subject, index) => <li key={index}>{subject}</li>)
                                            }
                                        </ul>
                                    </>
                                ) : null
                            }
                            {
                                duplicateSubjects.length > 0 ? (
                                    duplicateSubjects.map(renderDuplicateSubjectItem)
                                ) : null
                            }
                            <div className="s-hflex-end">
                                <div className="text clickable">
                                    Ігнорувати
                                </div>
                            </div>
                        </Alert>
                        <Spacer height={15} />
                    </>
                ) : null
            }
            <Textarea noMargin={true} placeholder="Питання" value={test.question}
                      onChange={event => setTest({...test, question: event.target.value})}/>
            <ErrorsArea errors={formState.errors.question}/>
            <Spacer height={10}/>
            <SvgContentChooser
                value={test.questionSvg}
                setValue={content => updateTestProperty("questionSvg", content)}/>
            <Spacer height={20}/>
            <Editor
                placeholder="Коментар"
                value={test.comment}
                onChange={updateTestComment}/>
            <ErrorsArea errors={formState.errors.comment}/>
            <Spacer height={10}/>
            <SvgContentChooser
                value={test.commentSvg}
                setValue={content => updateTestProperty("commentSvg", content)}/>
            <Spacer height={20}/>
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
                <ErrorsArea errors={formState.errors.variantsCorrectness}/>
                <Spacer height={20}/>
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
            <ErrorsArea errors={formState.errors.subjects}/>
            <Spacer height={20}/>
            <div className="s-hflex-end">
                <LoaderBoundary condition={formState.loading} size="small">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <>
                                    <Button onClick={saveDraft}>Зберегти чернетку</Button>
                                    <Spacer width={10} />
                                    <Button onClick={() => save(setPopupState)} isFilled={true}>Зберегти</Button>
                                </>
                            )
                        }
                    </PopupConsumer>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default TestForm;
