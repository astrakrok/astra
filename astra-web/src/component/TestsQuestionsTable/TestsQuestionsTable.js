import Table from "../Table/Table";
import TestingTestRow from "./TestingTestRow/TestingTestRow";
import "./TestsQuestionsTable.css";

const TestsQuestionsTable = ({
    tests,
    orderFrom,
    onDelete = () => {}
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
                tests.map((item, index) => <TestingTestRow
                    key={item.id}
                    onDelete={onDelete}
                    test={item}
                    order={orderFrom + index + 1}/>)
            }
            </tbody>
        </Table>
    );
}

export default TestsQuestionsTable;
