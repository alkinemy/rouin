import React from "react";
import Link from "next/link"
import BankLinkButton from "./BankLinkButton";

const Header: React.FC = () => {
    return (
        <div className="relative bg-white">
            <div className="max-w-7xl mx-auto px-4 sm:px-6">
                <div className="flex justify-between items-center border-b-2 border-gray-100 py-6 md:justify-start md:space-x-10">
                    <nav className="md:flex justify-start space-x-10">
                        <Link href="#">
                            <a className="text-base font-medium text-gray-500 hover:text-gray-900">
                                Transactions
                            </a>
                        </Link>
                    </nav>
                    <div className="md:flex items-center justify-end md:flex-1 lg:w-0">
                        <a href="#" className="whitespace-nowrap text-base font-medium text-gray-500 hover:text-gray-900">
                            Dummy
                        </a>
                        <BankLinkButton/>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Header;