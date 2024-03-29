import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {page} from "../../../constant/page";
import Badge from "../../Badge/Badge";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import ActionDialog from "../../popup-component/ActionDialog/ActionDialog";
import Spacer from "../../Spacer/Spacer";
import Tooltipped from "../../Tooltipped/Tooltipped";
import "./TestRow.css";

export const TestRow = ({
    index,
    test,
    setPopupState,
    onDelete = () => {}
}) => {
    const [loading, setLoading] = useState(false);

    const statusClass = test.status === 'ACTIVE' ? 'active' : 'draft';

    const navigate = useNavigate();

    const openEditTestPage = test => {
        return navigate(page.admin.tests.id(test.id).edit);
    }

    const renderTestSpecialization = specialization => {
        return (
            <div key={specialization.id} className="s-hflex-center">
                <Badge className="full-width center">
                    {specialization.title}
                </Badge>
            </div>
        );
    }

    const confirmDelete = async () => {
        setLoading(true);
        await onDelete(test.id);
        setLoading(false);
    }

    const tryDelete = async () => {
        setPopupState({
            bodyGetter: () => <ActionDialog setPopupState={setPopupState} message="Ви дійсно впевнені, що хочете видалити даний тест? Дану операцію неможливо буде відмінити" onConfirm={confirmDelete} />
        });
    }

    return (
        <tr className={statusClass + " TestRow"}>
            <td className="center order">{index + 1}</td>
            <td className="line-break full-width question">{test.question}</td>
            <td>
                <div className="s-vflex-center">
                    {
                        test.specializations.map(renderTestSpecialization)
                    }
                </div>
            </td>
            <td>
                <div className="s-hflex-end">
                    <LoaderBoundary condition={loading} size="small">
                        <DisplayBoundary condition={test.status === "DRAFT"}>
                            <Tooltipped tooltip="Видалити" position="top">
                                <i className="material-icons clickable delete" onClick={tryDelete}>close</i>
                            </Tooltipped>
                        </DisplayBoundary>
                        <Spacer width={10} />
                        <Link to={page.admin.tests.id(test.id).edit} className="view">
                            <Tooltipped tooltip="Переглянути" position="top" onClick={() => openEditTestPage(test)}>
                                <i className="material-icons clickable">visibility</i>
                            </Tooltipped>
                        </Link>
                    </LoaderBoundary>
                </div>
            </td>
        </tr>
    );
}

export default TestRow;
