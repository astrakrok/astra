import {useState} from "react";
import InfoHeader from "../../InfoHeader/InfoHeader";
import RadioButton from "../../RadioButton/RadioButton";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import "./ImportTestsForm.css";
import Spacer from "../../Spacer/Spacer";
import Button from "../../Button/Button";
import FileChooser from "../../FileChooser/FileChooser";
import {importFromFile} from "../../../service/import.service";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";

const ImportTestsForm = () => {
    const [loading, setLoading] = useState(false);
    const [title, setTitle] = useState("");
    const [mode, setMode] = useState("file");
    const [file, setFile] = useState(null);

    const handleImportClick = async setPopupState => {
        setLoading(true);
        const result = await importFromFile(title, file);
        setLoading(false);
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={result.id ? `Тести було успішно імпортовано. Оновіть таблицю з історією для перегляду результатів` : `На жаль, не вдалося імпортувати тести`} />
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
            <div className="s-hflex-end">
                <LoaderBoundary condition={loading} size="small">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={event => handleImportClick(setPopupState)}>
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
