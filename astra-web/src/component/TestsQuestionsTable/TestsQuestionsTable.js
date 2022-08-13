import Table from "../Table/Table";
import TestingTestRow from "./TestingTestRow/TestingTestRow";
import "./TestsQuestionsTable.css";

const TestsQuestionsTable = ({
                                 testingsTests,
                                 onDelete = () => {
                                 }
                             }) => {
    return (
        <Table type="secondary" className="TestsQuestionsTable">
            <thead>
            <tr>
                <th>#</th>
                <th>Питання</th>
                <th>Видалити</th>
            </tr>
            </thead>
            <tbody>
            {
                testingsTests.map((item, index) => <TestingTestRow
                    key={item.id}
                    onDelete={onDelete}
                    testingTest={item}
                    order={index + 1}/>)
            }
            </tbody>
        </Table>
    );
}

export default TestsQuestionsTable;
