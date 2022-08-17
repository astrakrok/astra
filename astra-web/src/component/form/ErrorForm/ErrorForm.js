import {useState} from "react";
import Button from "../../Button/Button";
import FileChooser from "../../FileChooser/FileChooser";
import RadioButton from "../../RadioButton/RadioButton";
import Spacer from "../../Spacer/Spacer";
import Textarea from "../../Textarea/Textarea";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import {sendNotification} from "../../../service/notification.service";
import {notificationSchema} from "../../../validation/schema/notification";
import V from "max-validator";
import "./ErrorForm.css";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";

const problems = [
    "Відповідь неправильна",
    "У тесті є опечатка",
    "Питання/пояснення/варіанти/коментар не є зрозумілими",
    "Щось не працює/здається зламаним"
];

const ErrorForm = ({
                       initialValue = ""
                   }) => {
    const [file, setFile] = useState(null);
    const [value, setValue] = useState(initialValue);
    const [selectedProblem, setSelectedProblem] = useState(problems[0]);
    const [formState, setFormState] = useState({
        loading: false,
        errors: {}
    });

    const updateFile = event => {
        const files = event.target.files;
        if (files.length === 0) {
            setFile(null);
        } else {
            setFile(files[0]);
        }
    }

    const renderProblem = (item, index) => (
        <RadioButton
            key={index}
            contentClassName="problem"
            name="problem"
            onChange={() => setSelectedProblem(item)}
            checked={selectedProblem === item}
        >
            {item}
        </RadioButton>
    );

    const notify = async () => {
        setFormState({
            loading: true,
            errors: {}
        });
        const data = {
            title: selectedProblem,
            text: value,
            file: file
        };
        const validationResult = V.validate(data, notificationSchema);
        if (validationResult.hasError) {
            setFormState({
                loading: false,
                errors: validationResult.errors
            });
            return;
        }
        const result = await sendNotification(data);
        setFormState({
            loading: false,
            errors: {
                global: result.error
            },
            message: result.message
        });
    }

    return (
        <div className="ErrorForm s-vflex">
            <div className="title">Повідомити про помилку в цьому питанні</div>
            <div className="description">Не забудьте прочитати пояснення, коментарі та ще раз перевірити свою
                відповідь
            </div>
            <Spacer height={5}/>
            <div className="error-options">
                Що саме не так?
                <div className="options s-vflex">
                    {
                        problems.map(renderProblem)
                    }
                </div>
            </div>
            <div className="notice">
                Також, Ви можете повідомити про будь-які технічні проблеми:
            </div>
            <Spacer height={20}/>
            <FileChooser
                title={file == null ? "Вибрати фото" : file.name}
                onSelect={updateFile}/>
            <Spacer height={10}/>
            <Textarea
                className="problem-description"
                noMargin={true}
                withLabel={false}
                placeholder="Опис проблеми"
                value={value}
                onChange={event => setValue(event.target.value)}/>
            <ErrorsArea errors={formState.errors.text}/>
            <Spacer height={10}/>
            <LoaderBoundary condition={formState.loading} className="s-hflex-center" size="small">
                <div className="s-vflex">
                    <div className="feedback-result">
                        {
                            formState.errors.global ? (
                                <div className="failure">
                                    {formState.errors.global}
                                </div>
                            ) : (
                                formState.message ? (
                                    <div className="success">
                                        {formState.message}
                                    </div>
                                ) : null
                            )
                        }
                    </div>
                    <div className="s-hflex-center">
                        <Button isFilled={true} onClick={notify}>
                            Відправити
                        </Button>
                    </div>
                </div>
            </LoaderBoundary>
        </div>
    );
}

export default ErrorForm;
