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
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import {convertToUserTimezone} from "../../../handler/date.handler";
import {compareFullSubjects} from "../../../handler/sort.handler";
import TabPanel from "../../TabPanel/TabPanel";
import TestingsList from "./TestingsList/TestingsList";
import {mapToTestErrors} from "../../../mapper/error.test.mapper";

const TAB_MODES = {
    INFO: "info",
    EXAMS: "exams"
}

const TestForm = ({
                      initialTest = defaultEmptyTest,
                      onSend = () => {
                      },
                      onSendDraft = () => {
                      }
                  }) => {
    const [test, setTest] = useState(initialTest);
    const [mode, setMode] = useState(TAB_MODES.INFO);
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
                bodyGetter: () => <MessagePopupBody message="Збереження не можливе. Тест містить помилки"/>
            });
            return;
        }
        const savedTest = await onSend(test);
        if (savedTest.id) {
            setTest({
                ...savedTest
            });
        }
        setFormState({
            loading: false,
            errors: savedTest.id ? {} : mapToTestErrors(savedTest)
        });
    }

    const saveDraft = async () => {
        setFormState({
            loading: true,
            errors: {}
        });
        const updatedTest = await onSendDraft(test);
        setFormState({
            loading: false,
            errors: {}
        });
        if (updatedTest.id) {
            setTest({
                ...updatedTest
            });
        }
    }

    const updateVariant = (values, index) => {
        setTest(previous => {
            previous.variants[index] = {
                ...previous.variants[index],
                ...values,
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
            <Badge key={subject.id} type="green" limited={false}>
                <div className="s-hflex full-width">
                    <span className="long-text">{subject.specialization.step.title} | {subject.specialization.title} | {subject.title}</span>
                    <div className="delete-subject s-vflex-center clickable" onClick={() => removeSubject(index)}>
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

    const renderDuplicateSubjectItem = subject => (
        <>
            <div>Предмет <strong>{subject.title}</strong> був знайденим у наступних спеціалізаціях:</div>
            <ul className="browser-default errors-list">
                {
                    subject.items.map(item => <li
                        key={item.specializationId}>{item.specializationTitle} ({item.stepTitle})</li>)
                }
            </ul>
        </>
    );

    const tryOpenExamsTab = setPopupState => {
        if (test.changed) {
            setPopupState({
                bodyGetter: () => <MessagePopupBody message="Збережіть зміни для того, щоб почати редагування іспитів" />
            });
            return;
        }
        setMode(TAB_MODES.EXAMS);
    }

    const getTabs = setPopupState => {
        const examsActive = test.id && test.status !== 'DRAFT';
        return [
            {
                title: "Основна інформація",
                onClick: () => setMode(TAB_MODES.INFO)
            },
            {
                title: "Іспити" + (examsActive ? "" : " (Зміни не збережено)"),
                onClick: () => tryOpenExamsTab(setPopupState),
                disabled: !examsActive,
            }
        ];
    };

    return (
        <div className="TestForm s-vflex full-height">
            <div className="full-width">
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <TabPanel type="primary" tabs={getTabs(setPopupState)} stretch={true} />
                        )
                    }
                </PopupConsumer>
            </div>
            <Spacer height={20} />
            <div className="full-width s-hflex-end info">
                <DisplayBoundary condition={test.createdDate}>
                    <span>Тест було створено: {convertToUserTimezone(test.createdDate, "HH:mm DD/MM/YYYY")}</span>
                    <Spacer width={5} />
                </DisplayBoundary>
                <span>{test.status === "DRAFT" ? " (Чернетка)" : null}</span>
            </div>
            <Spacer height={10} />
            {
                (notFoundSubjects.length || duplicateSubjects.length) ? (
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
                        <Spacer height={15}/>
                    </>
                ) : null
            }
            <DisplayBoundary condition={mode === TAB_MODES.INFO}>
                <DisplayBoundary condition={test.subjectsChanged}>
                    <Spacer height={20} />
                    <Alert type="warning">
                        <span className="weight-strong">УВАГА!</span> Ви зробили зміни у списку предметів для даного тесту. Збережіть тест для того, щоб мати можливість редагувати список іспитів.
                    </Alert>
                    <Spacer height={20} />
                </DisplayBoundary>
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
                <div className="s-hflex wrap-flex">
                    {
                        test.subjects.sort(compareFullSubjects).map(renderSubject)
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
                <DisplayBoundary condition={formState.errors.redundantTestings}>
                    <Spacer height={20}/>
                    <Alert type="warning">
                        Редагування призвело до змін у списку спеціалізацій даного тесту. Для успішного завернення редагування потрібно видалити тест з наступних іспитів:
                        <ul>
                            {
                                formState.errors.redundantTestings ? (
                                    formState.errors.redundantTestings.map((item, index) => <li key={index}>{item.exam.title}: {item.specialization.step.title} | {item.specialization.title}</li>)
                                ) : null
                            }
                        </ul>
                    </Alert>
                </DisplayBoundary>
                <Spacer height={20}/>
                <div className="s-hflex-end">
                    <LoaderBoundary condition={formState.loading} size="small">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <>
                                        <DisplayBoundary condition={test.status !== 'ACTIVE'}>
                                            <Button
                                                onClick={saveDraft}>{test.id ? "Зберегти" : "Створити"} чернетку</Button>
                                            <Spacer width={10}/>
                                        </DisplayBoundary>
                                        <Button onClick={() => save(setPopupState)}
                                                isFilled={true}>{test.id ? "Зберегти" : "Створити"}</Button>
                                    </>
                                )
                            }
                        </PopupConsumer>
                    </LoaderBoundary>
                </div>
            </DisplayBoundary>
            <DisplayBoundary condition={mode === TAB_MODES.EXAMS}>
                <TestingsList testId={test.id} initialTestings={test.testings} />
            </DisplayBoundary>
        </div>
    );
}

export default TestForm;
