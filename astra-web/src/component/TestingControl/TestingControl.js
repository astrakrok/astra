import Button from "../Button/Button";
import Spacer from "../Spacer/Spacer";
import "./TestingControl.css";

const TestingControl = ({
    onNew = () => {},
    onRepeat = () => {}
}) => {
    return (
        <div className="s-hflex-center">
            <Button onClick={onNew}>
                Почати новий іспит
            </Button>
            <Spacer width={20} />
            <Button isFilled={true} onClick={onRepeat}>
                Пройти знову
            </Button>
        </div>
    );
}

export default TestingControl;
