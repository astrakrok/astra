import {useRef} from "react";
import AuthConsumer from "../../../context/auth/AuthConsumer";
import Button from "../../Button/Button";
import PhotoPreview from "../../PhotoPreview/PhotoPreview";
import "./ChangeUserAvatarForm.css";

const ChangeUserAvatarForm = () => {
    const fileInput = useRef(null);

    const openFilePicker = () => {
        if (fileInput == null) {
            return;
        }
        fileInput.current.click();
    }

    const changeUserAvatar = event => {
        const files = event.target.files;
        if (files.length !== 1) {
            return;
        }
        console.log(files[0].name);
    }

    return (
        <div className="ChangeUserAvatarForm s-hflex-center">
            <AuthConsumer>
                {
                    ({userData}) => (
                        <>
                            <PhotoPreview src={userData.pictureUrl} />
                            <div className="s-vflex-center form">
                                <input type="file" ref={fileInput} onChange={changeUserAvatar} />
                                <Button isFilled={true} onClick={openFilePicker}>
                                    Вибрати фото
                                </Button>
                            </div>
                        </>
                    )
                }
            </AuthConsumer>
        </div>
    );
}

export default ChangeUserAvatarForm;
