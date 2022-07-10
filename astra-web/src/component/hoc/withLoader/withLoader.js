import { useEffect, useState } from "react";
import Preloader from "../../Preloader/Preloader";
import "./withLoader.css";

const withLoader = (
    Component,
    fetch = async () => {},
) => {
    return props => {
        const [data, setData] = useState(null);

        useEffect(() => {
            const runFetcher = async () => {
                const fetchedData = await fetch();

                setData(fetchedData.data);
            }
            
            runFetcher();
        }, []);

        return (
            data == null ? (
                <Preloader />
            ) : (
                <Component data={data} {...props} />
            )
        );
    };
}

export default withLoader;
