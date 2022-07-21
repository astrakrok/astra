import {useEffect, useState} from "react";
import {page} from "../../../../../constant/page";
import {getDetailedTests} from "../../../../../service/test.service";
import Button from "../../../../Button/Button";
import withTitle from "../../../../hoc/withTitle/withTitle";
import TestsList from "../../../../TestsList/TestsList";
import "./AllTestsPage.css";

const AllTestsPage = () => {
    const [tests, setTests] = useState([]);

    useEffect(() => {
        const fetchTests = async () => {
            const result = await getDetailedTests();
            setTests(result);
        }

        fetchTests();
    }, []);

    return (
        <div className="AllTestsPage container">
            <div className="row">
                <div className="s-hflex-end">
                    <Button to={page.admin.tests.create} isFilled={true}>Створити</Button>
                </div>
                <div className="tests-list">
                    <TestsList tests={tests} />
                </div>
            </div>
        </div>
    );
}

export default withTitle(AllTestsPage, "Тести");
