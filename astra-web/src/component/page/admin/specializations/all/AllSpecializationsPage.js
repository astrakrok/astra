import SpecializationItem from "../../../../SpecializationItem/SpecializationItem";
import Spacer from "../../../../Spacer/Spacer";
import "./AllSpecializationsPage.css";
import withTitle from "../../../../hoc/withTitle/withTitle";
import CreateSpecializationForm from "../../../../form/CreateSpecializationForm/CreateSpecializationForm";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import {useEffect, useState} from "react";
import {getAllByStepId as getSpecializations} from "../../../../../service/specialization.service";
import {getAll as getSteps} from "../../../../../service/step.service";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import TabPanel from "../../../../TabPanel/TabPanel";
import CreateStepForm from "../../../../form/CreateStepForm/CreateStepForm";
import ItemBlock from "../../../../ItemBlock/ItemBlock";

const AllSpecializationsPage = () => {
    const [steps, setSteps] = useState(null);
    const [selectedStep, setSelectedStep] = useState(null);
    const [specializations, setSpecializations] = useState(null);

    const fetchSpecializations = async () => {
        if (selectedStep == null) {
            return;
        }
        const data = await getSpecializations(steps[selectedStep].id);
        setSpecializations(data);
    }

    const fetchSteps = async () => {
        const data = await getSteps();
        setSteps(data);
        setSelectedStep(0);
    }

    useEffect(() => {
        fetchSteps();
    }, []);

    useEffect(() => {
        fetchSpecializations();
    }, [selectedStep]);

    const getSpecializationItem = (specialization, index) => {
        return (
            <div className="col s12 m6 l4 item" key={index}>
                <SpecializationItem specialization={specialization} />
            </div>
        );
    };

    const getSpecializationModalBody = (setPopupState) => {
        const onSuccessSpecializationCreation = () => {
            setSpecializations(null);
            setPopupState();
            fetchSpecializations();
        };

        return (
            <CreateSpecializationForm stepId={steps[selectedStep].id} onSuccess={onSuccessSpecializationCreation} />
        );
    }

    const getStepModalBody = setPopupState => {
        const onSuccessStepCreation = () => {
            setSteps(null);
            setPopupState();
            fetchSteps();
        };

        return (
            <CreateStepForm onSuccess={onSuccessStepCreation} />
        );
    }

    const openSpecializationPopup = setPopupState => {
        setPopupState({
            bodyGetter: getSpecializationModalBody
        });
    }

    const openStepPopup = setPopupState => {
        setPopupState({
            bodyGetter: getStepModalBody
        });
    }

    const getTabs = () => {
        return steps == null ? [] : [...steps.map((step, index) => ({
            ...step,
            onClick: () => {
                setSelectedStep(index);
            }
        })), {
            title: (
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <div onClick={() => openStepPopup(setPopupState)}>+</div>
                        )
                    }
                </PopupConsumer>
            ),
            passive: true
        }];
    }

    return (
        <div className="AllSpecializationsPage container">
            <div className="row">
                <LoaderBoundary className="full-width center" condition={steps == null} size="medium">
                    <TabPanel tabs={getTabs()} selected={setSelectedStep} />
                </LoaderBoundary>
                <Spacer height={20} />
                <div className="center">
                    <LoaderBoundary condition={specializations == null && steps != null}>
                        <>
                            {
                                (specializations) ? (
                                    <>
                                        {
                                            specializations.map(getSpecializationItem)
                                        }
                                        <div className="col s12 m6 l4 item">
                                            <PopupConsumer>
                                                {
                                                    ({setPopupState}) => (
                                                        <ItemBlock className="plus-block clickable" onClick={() => openSpecializationPopup(setPopupState)}>
                                                            +
                                                        </ItemBlock>
                                                    )
                                                }
                                            </PopupConsumer>
                                        </div>
                                    </>
                                ) : null
                            }
                        </>
                    </LoaderBoundary>
                </div>
            </div>
        </div>
    );
}

export default withTitle(AllSpecializationsPage, "Спеціалізації");
