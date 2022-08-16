import {useEffect, useState} from "react";
import LoaderBoundary from "../../../LoaderBoundary/LoaderBoundary";
import ExaminationThresholdForm from "../../../form/ExaminationThresholdForm/ExaminationThresholdForm";
import withTitle from "../../../hoc/withTitle/withTitle";
import "./ConfigurationPage.css";
import {getValues} from "../../../../service/property.service";
import {property} from "../../../../constant/property";
import ConfigPropertyEditorForm from "../../../form/ConfigPropertyEditorForm/ConfigPropertyEditorForm";
import Spacer from "../../../Spacer/Spacer";

const ConfigurationPage = () => {
    const [properties, setProperties] = useState({});
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchProperties = async () => {
            setLoading(true);
            const result = await getValues([
                property.examinationThresholdPercentage,
                property.trainingDescription,
                property.examinationDescription
            ]);
            setProperties(result);
            setLoading(false);
        }

        fetchProperties();
    }, []);

    return (
        <div className="container">
            <div className="row">
                <LoaderBoundary condition={loading} className="s-hflex-center">
                    <ExaminationThresholdForm initialValue={properties.examinationThresholdPercentage}/>
                    <Spacer height={20}/>
                    <ConfigPropertyEditorForm
                        initialValue={properties.trainingDescription}
                        propertyName={property.trainingDescription}
                        propertyHeader="Опис тренування"/>
                    <Spacer height={20}/>
                    <ConfigPropertyEditorForm
                        initialValue={properties.examinationDescription}
                        propertyName={property.examinationDescription}
                        propertyHeader="Опис екзамену"/>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default withTitle(ConfigurationPage, "Конфігурація сайту");
