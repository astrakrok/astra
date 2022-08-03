import Button from "../../Button/Button";
import "./TestCompletionWarning.css";

const TestCompletionWarning = ({
    onClick = () => {}
}) => {
    return (
        <div className="TestCompletionWarning s-vflex">
            <span className="warning center">Ви дійсно впевнені, що хочете закінчити тест? Відповіді, які не встигли обробитись, вже не будуть оброблені</span>
            <div className="s-hflex-center">
                <Button onClick={onClick} isFilled={true}>
                    Закінчити
                </Button>
            </div>
        </div>
    );
}

export default TestCompletionWarning;
