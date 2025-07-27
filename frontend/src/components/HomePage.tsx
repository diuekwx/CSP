import CalendarHeatmap from 'react-calendar-heatmap';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/HeatMap.css';
import Navbar from './NavBar';

interface Dates{
    date: string,
    count: number
}

type RawContribution = {
  date: string;  
  count: number;
};

type HeatmapValue = {
  date: string;  
  count: number;
};

export default function HomePage() {
    const today = new Date();
    const pad = (num: number) => String(num).padStart(2, '0');
    const navigate = useNavigate();

    const [dates, setDates] = useState<Dates[]>([]);
    const[year, setYears] = useState<number>(today.getFullYear());
    const [startDate, setStartDate] = useState<string>(`${today.getFullYear()}-01-01`);
    const [endDate, setEndDate] = useState<string>
        (`${today.getFullYear()}-${pad(today.getMonth() + 1)}-${pad(today.getDate())}`);

    useEffect(() => {
        const fetchData = async () => {
            try {
            const token = localStorage.getItem("jwt");

            const response = await fetch("http://localhost:8080/contribution/user-contributions", {
                method: "GET",
                headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
                },
            });

            if (!response.ok) {
                throw new Error(`HTTP error ${response.status}`);
            }

            const data: RawContribution[] = await response.json();

            const formattedData: HeatmapValue[] = Object.entries(data).map(
            ([date, count]) => ({
                date: date.slice(0, 10), 
                count: Number(count),    
            })
            );
            setDates(formattedData);
            
            } catch (error) {
            console.error("Failed to fetch user contributions:", error);
            }
        };

        fetchData();
    }, []);

    const viewProjects = () => {
        navigate('/projects');
    }


    const handleCreateRepo = () => {
        navigate('/new')
    }

    console.log(dates);

    return (
        <>

    <Navbar/>
    <div className="flex flex-col items-center p-8 min-h-screen bg-gray-50 text-gray-800">
        
        <h1 className="text-3xl font-semibold mb-6">CSPHub</h1>

        <div className="mb-4 flex gap-4">
        <button
            onClick={handleCreateRepo}
            className="bg-gray-800 hover:bg-black text-white font-medium py-2 px-4 rounded"
        >
            Create New Project
        </button>
        <button
            onClick={viewProjects}
            className="bg-white border border-gray-400 hover:border-gray-600 text-gray-800 font-medium py-2 px-4 rounded"
        >
            View Projects
        </button>
        </div>

        <div className="w-full overflow-x-auto">
        <div className="flex flex-col items-center gap-2">
            <p className="text-lg font-semibold">{year}</p>
            <CalendarHeatmap
            startDate={new Date(startDate.toString())}
            endDate={new Date(endDate.toString())}
            values={dates}
            classForValue={(value) => {
                if (!value || value.count === 0) return 'color-empty';
                else if (value.count < 2) return 'color-scale-1';
                else if (value.count < 3) return 'color-scale-2';
                else if (value.count < 4) return 'color-scale-3';
                else return 'color-scale-4';
            }}
            />
            <div className="flex items-center gap-1 mt-2 text-sm">
            <span>Less</span>
            <span className="w-4 h-4 color-empty inline-block rounded-sm"></span>
            <span className="w-4 h-4 color-scale-1 inline-block rounded-sm"></span>
            <span className="w-4 h-4 color-scale-2 inline-block rounded-sm"></span>
            <span className="w-4 h-4 color-scale-3 inline-block rounded-sm"></span>
            <span className="w-4 h-4 color-scale-4 inline-block rounded-sm"></span>
            <span>More</span>
            </div>
        </div>
        </div>
    </div>
    </>
    );
}