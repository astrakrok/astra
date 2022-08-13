import {useState} from "react";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Tooltipped from "../../Tooltipped/Tooltipped";
import "./TestingTestRow.css";

const TestingTestRow = ({
                            order = 1,
                            onDelete = () => {
                            },
                            testingTest
                        }) => {
    const [loading, setLoading] = useState(false);

    const handleDelete = async () => {
        setLoading(true);
        await onDelete(testingTest.id);
        setLoading(false);
    }

    return (
        <tr>
            <td className="center">{order}</td>
            <td className="line-break">{testingTest.test.question}</td>
            <td>
                <div className="delete s-hflex-center">
                    <LoaderBoundary condition={loading} size="small">
                        <Tooltipped tooltip="Видалити" position="top">
                            <i className="material-icons clickable" onClick={handleDelete}>close</i>
                        </Tooltipped>
                    </LoaderBoundary>
                </div>
            </td>
        </tr>
    );
}

export default TestingTestRow;
