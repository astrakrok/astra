import {useNavigate} from "react-router-dom";
import {page} from "../../constant/page";
import Badge from "../Badge/Badge";
import "./TestsList.css";

const TestsList = ({tests}) => {
    const navigate = useNavigate();

    const renderTestSpecialization = specialization => {
        return (
            <div key={specialization.id} className="s-hflex-center">
                <Badge>
                    {specialization.title}
                </Badge>
            </div>
        );
    }

    const renderTestExam = exam => {
        return (
            <div key={exam.id} className="s-hflex-center">
                <Badge type="primary">
                    {exam.title}
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
                            test.exams.map(renderTestExam)
                        }
                    </div>
                </td>
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
        <table className="TestsList">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Питання</th>
                    <th>Іспити</th>
                    <th>Спеціалізації</th>
                </tr>
            </thead>
            <tbody>
                {
                    tests.map(renderTestTableItem)
                }
            </tbody>
        </table>
    );
}

export default TestsList;
