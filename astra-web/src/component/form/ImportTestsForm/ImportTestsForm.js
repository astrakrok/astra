import {useState} from "react";
import InfoHeader from "../../InfoHeader/InfoHeader";
import RadioButton from "../../RadioButton/RadioButton";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import "./ImportTestsForm.css";
import Spacer from "../../Spacer/Spacer";
import Button from "../../Button/Button";
import FileChooser from "../../FileChooser/FileChooser";
import {importFromFile, importFromWeb} from "../../../service/transfer.service";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import V from "max-validator";
import {importSchema} from "../../../validation/schema/import";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";

const ImportTestsForm = () => {
    const [formState, setFormState] = useState({loading: false, errors: {}});
    const [title, setTitle] = useState("");
    const [mode, setMode] = useState("file");
    const [file, setFile] = useState(null);
    const [url, setUrl] = useState("");

    const mapErrorsToMessage = errors => {
        const error = errors[0];
        if (error.type === "UNKNOWN_SOURCE") {
            return "Ресурс не відомий";
        }
        return "Сталась невідома помилка. Спробуйте пізніше.";
    }

    const checkErrors = () => {
        const validationResult = V.validate({title}, importSchema);
        const fileError = (mode === "file" && file == null) ? {
            file: ["Виберіть файл"]
        } : {}
        return {
            ...validationResult.errors,
            ...fileError
        }
    }

    const handleImportClick = async setPopupState => {
        setFormState({loading: true, errors: {}});
        const errors = checkErrors();
        if (Object.keys(errors).length > 0) {
            setFormState({
                loading: false,
                errors: errors
            });
            return;
        }
        const result = mode === "file" ? (await importFromFile(title, file)) : (await importFromWeb(title, url));
        setFormState({loading: false, errors: {}});
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={result.id ? `Тести було успішно імпортовано. Оновіть таблицю з історією для перегляду результатів` : mapErrorsToMessage(result.errors)} />
        });
    }

    const fileInputChanged = event => {
        event.preventDefault();
        if (event.target.files && event.target.files[0]) {
            setFile(event.target.files[0]);
        }
    }

    return (
        <div className="ImportTestsForm s-vflex full-width">
            <InfoHeader align="start">Імпорт</InfoHeader>
            <div className="specialization-select s-vflex equal-flex">
                <Input
                    placeholder="Найменування"
                    value={title}
                    onChange={event => setTitle(event.target.value)} />
                    <ErrorsArea errors={formState.errors.title}/>
            </div>
            <Spacer height={20} />
            <RadioButton
                onChange={() => setMode("file")}
                name="file"
                checked={mode === "file"}
            >
                З файлу
            </RadioButton>
            <DisplayBoundary condition={mode === "file"}>
                <Spacer height={15} />
                <div className="s-hflex">
                    <FileChooser
                        title={file == null ? "Вибрати файл" : file.name}
                        accept=".xls, .xlsx, .csv"
                        onSelect={fileInputChanged} />
                </div>
                <ErrorsArea errors={formState.errors.file}/>
                <Spacer height={10} />
            </DisplayBoundary>
            <Spacer height={20} />
            <RadioButton
                onChange={() => setMode("web")}
                name="web"
                checked={mode === "web"}
            >
                З веб-ресурсу
            </RadioButton>
            <DisplayBoundary condition={mode === "web"}>
                <Spacer height={15} />
                <Input
                    value={url}
                    withLabel={false}
                    onChange={event => setUrl(event.target.value)}
                    placeholder="Посилання" />
                <Spacer height={15} />
            </DisplayBoundary>
            <div className="s-hflex-end">
                <LoaderBoundary condition={formState.loading} size="small">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={() => handleImportClick(setPopupState)}>
                                    Імпортувати
                                </Button>
                            )
                        }
                    </PopupConsumer>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default ImportTestsForm;
