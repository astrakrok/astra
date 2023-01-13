import SingleSelect from "../../SingleSelect/SingleSelect";
import InfoHeader from "../../InfoHeader/InfoHeader";
import "./ExportTestsForm.css";
import {useEffect, useState} from "react";
import {getAll} from "../../../service/step.service";
import {getAllByStepId} from "../../../service/specialization.service";
import Spacer from "../../Spacer/Spacer";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import Button from "../../Button/Button";
import V from "max-validator";
import {exportToFile} from "../../../service/export.service";
import {exportSchema} from "../../../validation/schema/export";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";

const fileTypes = [
    {
        value: "XLSX",
        label: "Excel (.xlsx)"
    },
    {
        value: "XLS",
        label: "Excel (.xls)"
    },
    {
        value: "CSV",
        label: "CSV (.csv)"
    }
];

const ExportTestsForm = () => {
    const [steps, setSteps] = useState(null);
    const [selectedStep, setSelectedStep] = useState(null);
    const [specializations, setSpecializations] = useState(null);
    const [selectedSpecialization, setSelectedSpecialization] = useState(null);
    const [fileType, setFileType] = useState(null);
    const [formState, setFormState] = useState({loading: false, errors: {}});

    useEffect(() => {
        const fetchSteps = async () => {
            const result = await getAll();

            setSteps(result.map(item => ({
                value: item.id,
                label: item.title
            })));
        }

        fetchSteps();
    }, []);

    useEffect(() => {
        const fetchSpecializations = async () => {
            if (!selectedStep) {
                return;
            }
            setSelectedSpecialization(null);
            setSpecializations(null);
            const result = await getAllByStepId(selectedStep.value);

            setSpecializations(result.map(item => ({
                value: item.id,
                label: item.title
            })));
        }

        fetchSpecializations();
    }, [selectedStep]);

    const downloadFile = (result, type) => {
        const href = URL.createObjectURL(result);

        const link = document.createElement('a');
        link.href = href;
        link.setAttribute('download', 'export-result.' + (type ? type.toLowerCase() : "txt"));
        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
        URL.revokeObjectURL(href);
    }

    const handleExportClick = async () => {
        setFormState({
            loading: true,
            errors: {}
        });
        const exportData = {
            specializationId: selectedSpecialization && selectedSpecialization.value,
            fileType: fileType && fileType.value
        };
        const validationResult = V.validate(exportData, exportSchema);
        if (validationResult.hasError) {
            setFormState({
                loading: false,
                errors: validationResult.errors
            });
            return;
        }
        const result = await exportToFile(exportData);
        downloadFile(result, exportData.fileType);
        setFormState({
            loading: false,
            errors: {}
        });
    }

    return (
        <div className="ExportTestsForm s-vflex full-width">
            <InfoHeader align="end">Експорт</InfoHeader>
            <div className="form">
                <SingleSelect
                    isLoading={steps == null}
                    placeholder="Виберіть КРОК"
                    isClearable={true}
                    options={steps}
                    onChange={setSelectedStep}
                    value={selectedStep}/>
                <Spacer height={20} />
                <SingleSelect
                    isLoading={specializations == null}
                    placeholder="Виберіть спеціалізацію"
                    isClearable={true}
                    options={specializations}
                    onChange={setSelectedSpecialization}
                    value={selectedSpecialization}/>
                    <Spacer height={20} />
                <div className="s-vflex">
                    <SingleSelect
                        className="equal-flex"
                        placeholder="Виберіть тип файлу"
                        isClearable={true}
                        options={fileTypes}
                        onChange={setFileType}
                        value={fileType}/>
                    <ErrorsArea errors={formState.errors.fileType}/>
                </div>
                <Spacer height={20} />
                <div className="s-hflex-end">
                    <LoaderBoundary condition={formState.loading} size="small">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <Button isFilled={true} onClick={() => handleExportClick(setPopupState)}>
                                        Експортувати
                                    </Button>
                                )
                            }
                        </PopupConsumer>
                    </LoaderBoundary>
                </div>
            </div>
        </div>
    );
}

export default ExportTestsForm;
