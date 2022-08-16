import Button from "../Button/Button";
import Spacer from "../Spacer/Spacer";
import {page} from "../../constant/page";
import "./TestingControl.css";

const TestingControl = ({
    onRepeat = () => {}
}) => {
    return (
        <div className="s-hflex-center">
            <Button to={page.newTesting}>
                Почати новий іспит
            </Button>
            <Spacer width={20}/>
            <Button isFilled={true} onClick={onRepeat}>
                Пройти знову
            </Button>
        </div>
    );
}

export default TestingControl;
