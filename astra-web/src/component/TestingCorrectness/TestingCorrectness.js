import TrainingTest from "../TrainingTest/TrainingTest";
import "./TestingCorrectness.css";

const TestingCorrectness = ({tests}) => {
    const toTestState = test => {
        return {
            test: test,
            userAnswer: test.userAnswer
        }
    }

    return (
        <>
            {
                tests.map((test, index) => <TrainingTest testState={toTestState(test)} checked={false} order={index + 1} />)
            }
        </>
    );
}

export default TestingCorrectness;
