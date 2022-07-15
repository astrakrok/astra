import SpecializationItem from "../../../../SpecializationItem/SpecializationItem";
import Button from "../../../../Button/Button";
import Spacer from "../../../../Spacer/Spacer";
import InfoText from "../../../../InfoText/InfoText";
import { page } from "../../../../../constant/page";
import "./AllSpecializationsPage.css";
import withTitle from "../../../../hoc/withTitle/withTitle";
import CreateSpecializationForm from "../../../../form/CreateSpecializationForm";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import { useEffect, useState } from "react";
import { getAll } from "../../../../../service/specialization.service";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";

const AllSpecializationsPage = () => {
    const [specializations, setSpecializations] = useState(null);

    const fetchSpecializations = async () => {
        const data = await getAll();
        setSpecializations(data);
    }

    useEffect(() => {
        fetchSpecializations();
    }, []);

    const getSpecializationItem = (specialization, index) => {
        return (
            <div className="col s12 m6 l4 item" key={index}>
                <SpecializationItem specialization={specialization} />
            </div>
        );
    };

    const getModalBody = (setPopupState) => {
        const onSuccessSpecializationCreation = () => {
            setSpecializations(null);
            setPopupState();
            fetchSpecializations();
        };

        return (
            <CreateSpecializationForm onSuccess={onSuccessSpecializationCreation} />
        );
    }

    const openPopup = setPopupState => {
        setPopupState({
            bodyGetter: getModalBody
        });
    }

    return (
        <div className="AllSpecializationsPage container">
            <div className="row">
                <div className="full-width center button">
                    <PopupConsumer>
                        {
                            ({setPopupState}) =>  (
                                <Button to={page.admin.specializations.create} isFilled={true} onClick={() => openPopup(setPopupState)}>
                                    Створити
                                </Button>
                            )
                        }
                    </PopupConsumer>
                </div>
                <Spacer height={40} />
                <div className="center">
                    <LoaderBoundary condition={specializations == null}>
                        <>
                            {
                                (specializations && specializations.length > 0) ? (
                                    specializations.map(getSpecializationItem)
                                ) : (
                                    <div className="center">
                                        <InfoText>
                                            Спеціалізації відсутні
                                        </InfoText>
                                    </div>
                                )
                            }
                        </>
                    </LoaderBoundary>
                </div>
            </div>
        </div>
    );
}

export default withTitle(AllSpecializationsPage, "Спеціалізації");
