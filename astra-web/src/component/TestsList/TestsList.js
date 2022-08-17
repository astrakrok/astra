import {useNavigate} from "react-router-dom";
import {page} from "../../constant/page";
import Badge from "../Badge/Badge";
import Table from "../Table/Table";
import "./TestsList.css";

const TestsList = ({tests}) => {
    const navigate = useNavigate();

    const renderTestSpecialization = specialization => {
        return (
            <div key={specialization.id} className="s-hflex-center">
                <Badge className="full-width">
                    {specialization.title}
                </Badge>
            </div>
        );
    }

    const openEditTestPage = test => {
        return navigate(page.admin.tests.id(test.id).edit);
    }

    const renderTestTableItem = (test, index) => {
        return (
            <tr key={test.id} className="clickable" onClick={() => openEditTestPage(test)}>
                <td className="center">{index + 1}</td>
                <td className="line-break">{test.question}</td>
                <td>
                    <div className="s-vflex-center">
                        {
                            test.specializations.map(renderTestSpecialization)
                        }
                    </div>
                </td>
            </tr>
        );
    }

    return (
        <Table type="secondary">
            <thead>
            <tr>
                <th>#</th>
                <th>Питання</th>
                <th>Спеціалізації</th>
            </tr>
            </thead>
            <tbody>
            {
                tests.map(renderTestTableItem)
            }
            </tbody>
        </Table>
    );
}

export default TestsList;
