import TrainingTest from "../TrainingTest/TrainingTest";
import "./TestingCorrectness.css";

const TestingCorrectness = ({tests}) => {
    return (
        <>
            {
                tests.map((test, index) => <TrainingTest key={index} testState={test} checked={false} order={index + 1} />)
            }
        </>
    );
}

export default TestingCorrectness;
