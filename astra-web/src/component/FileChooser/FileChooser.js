import {useRef} from "react";
import Button from "../Button/Button";
import "./FileChooser.css";

export const FileChooser = props => {
    const {
        title = "Вибрати файл",
        onSelect = () => {
        },
        ...otherProps
    } = props;

    const fileInput = useRef(null);

    const openFilePicker = () => {
        if (fileInput == null) {
            return;
        }
        fileInput.current.click();
    }

    return (
        <div className="s-vflex FileChooser">
            <input type="file" ref={fileInput} onChange={onSelect} {...otherProps}/>
            <Button onClick={openFilePicker}>
                {title}
            </Button>
        </div>
    );
}

export default FileChooser;
