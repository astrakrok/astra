import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {getFullDetailedTest} from "../../../../../service/test.service";
import EditTestForm from "../../../../form/EditTestForm/EditTestForm";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import withTitle from "../../../../hoc/withTitle/withTitle";
import "./EditTestPage.css";
import withScrollTopButton from "../../../../hoc/withScrollTopButton/withScrollTopButton";

const EditTestPage = () => {
    const {id} = useParams();

    const [test, setTest] = useState(null);

    useEffect(() => {
        const fetchTest = async () => {
            const test = await getFullDetailedTest(id);
            setTest(test);
        }

        fetchTest();
    }, []);

    return (
        <>
            <div className="EditTestPage container">
                <div className="row">
                    <LoaderBoundary condition={test == null} className="s-hflex-center">
                        {
                            test ? (
                                <EditTestForm test={test} />
                            ) : null
                        }
                    </LoaderBoundary>
                </div>
            </div>
            <div className="equal-flex" />
        </>
    );
}

export default withScrollTopButton(withTitle(EditTestPage, "Редагувати тест"));
