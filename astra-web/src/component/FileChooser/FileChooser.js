import {useRef} from "react";
import Button from "../Button/Button";
import "./FileChooser.css";

export const FileChooser = ({
                                title = "Вибрати файл",
                                onSelect = () => {
                                }
                            }) => {
    const fileInput = useRef(null);

    const openFilePicker = () => {
        if (fileInput == null) {
            return;
        }
        fileInput.current.click();
    }

    return (
        <div className="s-vflex FileChooser">
            <input type="file" ref={fileInput} onChange={onSelect}/>
            <Button onClick={openFilePicker}>
                {title}
            </Button>
        </div>
    );
}

export default FileChooser;
