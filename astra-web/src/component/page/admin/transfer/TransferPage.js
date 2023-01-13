import ExportTestsForm from "../../../form/ExportTestsForm/ExportTestsForm";
import ImportTestsForm from "../../../form/ImportTestsForm/ImportTestsForm";
import ResponsiveColumns from "../../../ResponsiveColumns/ResponsiveColumns";
import "./TransferPage.css";
import ImportHistory from "../../../ImportHistory/ImportHistory";
import InfoHeader from "../../../InfoHeader/InfoHeader";
import Spacer from "../../../Spacer/Spacer";
import withTitle from "../../../hoc/withTitle/withTitle";
import {Link} from "react-router-dom";
import {page} from "../../../../constant/page";

const TransferPage = () => {
    return (
        <div className="equal-flex TransferPage container">
            <ResponsiveColumns
                firstColumn={<ImportTestsForm />}
                secondColumn={<ExportTestsForm />}
                firstClassName="left-column"
                secondClassName="right-column"/>
            <Spacer height={20} />
            <div className="transfer-details center">
                Детальніше про імпорт/експорт можна почитати <Link target="_blank" to={page.admin.documentation.transfer} className="weight-strong">тут</Link>.
            </div>
            <Spacer height={50} />
            <InfoHeader>Історія імпорту</InfoHeader>
            <ImportHistory />
            <Spacer height={20} />
        </div>
    );
}

export default withTitle(TransferPage, "Імпорт/Експорт");
