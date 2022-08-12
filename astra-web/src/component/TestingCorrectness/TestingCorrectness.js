import TrainingTest from "../TrainingTest/TrainingTest";
import "./TestingCorrectness.css";

const TestingCorrectness = ({tests}) => {
    return (
        <>
            {
                tests.map((test, index) => <TrainingTest key={index} testState={test} answered={true} order={index + 1} />)
            }
        </>
    );
}

export default TestingCorrectness;
