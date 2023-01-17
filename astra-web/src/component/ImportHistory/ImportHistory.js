import "./ImportHistory.css";
import {getStats} from "../../service/transfer.service";
import ImportHistoryFilter from "../filter/ImportHistoryFilter/ImportHistoryFilter";
import Paginated from "../Paginated/Paginated";
import Table from "../Table/Table";
import "moment-timezone";
import {convertToUserTimezone} from "../../handler/date.handler";
import {Link} from "react-router-dom";
import {page} from "../../constant/page";

const ImportHistory = () => {
    const fetchPage = async (filter, pageable) => {
        return await getStats(filter, pageable);
    }

    const getHumanReadableSource = (source) => {
        switch (source) {
            case "EXCEL_FILE":
                return "XLS/XLSX файл";
            case "CSV_FILE":
                return "CSV файл";
            case "TESTING_UKR_WEB":
                return "тестування.укр";
            default:
                return "Ресурс невідомий";
        }
    }

    const renderHistoryItem = (item, index) => {
        return (
            <tr key={item.id}>
                <td>{index + 1}</td>
                <td>{item.title}</td>
                <td>{convertToUserTimezone(item.createdAt)}</td>
                <td><b>{item.sourceTitle}</b> ({getHumanReadableSource(item.source)})</td>
                <td>{item.activeCount}</td>
                <td>{item.draftCount}</td>
                <td>
                    <div className="view clickable">
                        <Link to={page.admin.tests.all + "?importId=" + item.id}>
                            <i className="material-icons">visibility</i>
                        </Link>
                    </div>
                </td>
            </tr>
        );
    }

    return (
        <div className="ImportHistory full-width">
            <Paginated pageSize={10} pageHandler={fetchPage}>
                {({
                    filter: ({setFilter}) => <ImportHistoryFilter onFilterSelected={setFilter} />,
                    content: ({items, orderFrom}) => (
                        <Table className="import-history-table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Найменування</th>
                                    <th>Дата</th>
                                    <th>Ресурс</th>
                                    <th>Активні</th>
                                    <th>Чернетки</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    items.map((item, index) => renderHistoryItem(item, orderFrom + index))
                                }
                            </tbody>
                        </Table>
                    )
                })}
            </Paginated>
        </div>
    );
}

export default ImportHistory;
