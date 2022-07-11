import SpecializationItem from "../../../../SpecializationItem/SpecializationItem";
import Button from "../../../../Button/Button";
import Spacer from "../../../../Spacer/Spacer";
import InfoText from "../../../../InfoText/InfoText";
import { page } from "../../../../../constant/page";
import { getAll } from "../../../../../service/specialization.service";
import "./AllSpecializationsPage.css";
import withLoader from "../../../../hoc/withLoader/withLoader";
import withTitle from "../../../../hoc/withTitle/withTitle";

const AllSpecializationsPage = () => {
    const getSpecializationItem = (specialization, index) => {
        return (
            <div className="col s12 m6 l4 item" key={index}>
                <SpecializationItem specialization={specialization} />
            </div>
        );
    };

    const getContent = ({data}) => {
        return data.length > 0 ? (
            data.map(getSpecializationItem)
        ) : (
            <div className="center">
                <InfoText>
                    Спеціалізації відсутні
                </InfoText>
            </div>
        );
    }

    const ContentHoc = withLoader(getContent, getAll);

    return (
        <div className="AllSpecializationsPage container">
            <div className="row">
                <div className="full-width center button">
                    <Button to={page.admin.specializations.create} isFilled={true}>
                        Створити
                    </Button>
                </div>
                <Spacer height={40} />
                <div className="center">
                    <ContentHoc />
                </div>
            </div>
        </div>
    );
}

export default withTitle(AllSpecializationsPage, "Спеціалізації");
