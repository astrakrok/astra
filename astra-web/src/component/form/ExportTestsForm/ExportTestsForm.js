import SingleSelect from "../../SingleSelect/SingleSelect";
import InfoHeader from "../../InfoHeader/InfoHeader";
import "./ExportTestsForm.css";
import {useEffect, useState} from "react";
import {getAll} from "../../../service/step.service";
import {getAllByStepId} from "../../../service/specialization.service";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import Spacer from "../../Spacer/Spacer";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import Button from "../../Button/Button";
import {exportToFile} from "../../../service/export.service";

const documentTypes = [
    {
        value: "EXCEL",
        label: "Excel"
    },
    {
        value: "CSV",
        label: "CSV"
    }
];

const excelTypes = [
    {
        value: "XLSX",
        label: "xlsx"
    },
    {
        value: "XLS",
        label: "xls"
    }
];

const ExportTestsForm = () => {
    const [steps, setSteps] = useState(null);
    const [selectedStep, setSelectedStep] = useState(null);
    const [specializations, setSpecializations] = useState(null);
    const [selectedSpecialization, setSelectedSpecialization] = useState(null);
    const [documentType, setDocumentType] = useState(null);
    const [excelType, setExcelType] = useState(null);
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
        link.setAttribute('download', 'export-result.' + type);
        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
        URL.revokeObjectURL(href);
    }

    const getFileType = exportData => {
        if (exportData.documentType === "CSV") {
            return "CSV";
        }
        return exportData.excelType;
    }

    const handleExportClick = async () => {
        setFormState({
            loading: true,
            errors: {}
        });
        const exportData = {
            specializationId: selectedSpecialization && selectedSpecialization.value,
            documentType: documentType && documentType.value,
            excelType: excelType && excelType.value
        };
        const result = await exportToFile(exportData);
        downloadFile(result, getFileType(exportData));
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
                <div className="s-hflex">
                    <SingleSelect
                        className="equal-flex"
                        placeholder="Виберіть тип документу"
                        isClearable={true}
                        options={documentTypes}
                        onChange={setDocumentType}
                        value={documentType}/>
                    <DisplayBoundary condition={documentType && documentType.value === "EXCEL"}>
                        <Spacer width={10} />
                        <SingleSelect
                            placeholder="Розширення"
                            isClearable={true}
                            options={excelTypes}
                            onChange={setExcelType}
                            value={excelType}/>
                    </DisplayBoundary>
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
