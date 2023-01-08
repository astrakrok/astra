import ExportTestsForm from "../../../form/ExportTestsForm/ExportTestsForm";
import ImportTestsForm from "../../../form/ImportTestsForm/ImportTestsForm";
import ResponsiveColumns from "../../../ResponsiveColumns/ResponsiveColumns";
import "./TransferPage.css";
import ImportHistory from "../../../ImportHistory/ImportHistory";
import InfoHeader from "../../../InfoHeader/InfoHeader";
import Spacer from "../../../Spacer/Spacer";
import withTitle from "../../../hoc/withTitle/withTitle";

const TransferPage = () => {
    return (
        <div className="equal-flex TransferPage container">
            <ResponsiveColumns
                firstColumn={<ImportTestsForm />}
                secondColumn={<ExportTestsForm />}
                isSeparated={true}/>
            <Spacer height={50} />
            <InfoHeader>Історія імпорту</InfoHeader>
            <ImportHistory />
            <Spacer height={20} />
        </div>
    );
}

export default withTitle(TransferPage, "Імпорт/Експорт");
