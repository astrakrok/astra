import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import Table from "../Table/Table";
import TestingTestRow from "./TestingTestRow/TestingTestRow";
import "./TestsQuestionsTable.css";

const TestsQuestionsTable = ({
    tests,
    status,
    orderFrom,
    onDelete = () => {}
}) => {
    return (
        <Table type="secondary" className="TestsQuestionsTable">
            <thead>
            <tr>
                <th>#</th>
                <th>Питання</th>
                <DisplayBoundary condition={status === "ACTIVE"}>
                    <th>Видалити</th>
                </DisplayBoundary>
            </tr>
            </thead>
            <tbody>
            {
                tests.map((item, index) => <TestingTestRow
                    status={status}
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
