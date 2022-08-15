import {useEffect, useState} from "react";
import LoaderBoundary from "../../../LoaderBoundary/LoaderBoundary";
import ExaminationThresholdForm from "../../../form/ExaminationThresholdForm/ExaminationThresholdForm";
import withTitle from "../../../hoc/withTitle/withTitle";
import "./ConfigurationPage.css";
import {getValues} from "../../../../service/property.service";
import {property} from "../../../../constant/property";

const ConfigurationPage = () => {
    const [properties, setProperties] = useState({});
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchProperties = async () => {
            setLoading(true);
            const result = await getValues([
                property.examinationThresholdPercentage
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
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default withTitle(ConfigurationPage, "Конфігурація сайту");
