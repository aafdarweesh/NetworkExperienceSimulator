package runall;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RunAll {

	static String FileTitle = "Average_End-To-End Throughput Average_Distance_Travelled"
			+ " Number_of_Delivered Number_of_Dropped_packets "
			+ "Number_of_Re-requested_packets Number_of_packets_Arrived_more_than_once "
			+ "Number_of_Nodes Measurement_Time Buffer_size "
			+ "Number_of_Packets_arrived_to_the_system Packet_Time_limit";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BufferSizeTest();
		NumberOfNodesTest();
		NumberOfPacketsTest();
		ArrivalRateTest();
		DifferentPacketsTest();

	}

	public static void BufferSizeTest() {

		PrepareResultFiles("BufferSizeTest");

		ArrayList<Integer> serviceList = new ArrayList<Integer>();
		ArrayList<Integer> packetTypeList = new ArrayList<Integer>();

		serviceList.add(15);
		packetTypeList.add(1000);

		int cnt = 1;
		while (cnt <= 20) {

			store.RunStore run1 = new store.RunStore();
			carApproach.RunCar run2 = new carApproach.RunCar();
			netexpMinDistant.RunNetExp run3 = new netexpMinDistant.RunNetExp();
			netexpMinWeight.RunNetExp run4 = new netexpMinWeight.RunNetExp();
			netexpRandom.RunNetExp run5 = new netexpRandom.RunNetExp();
			randomApproach.RunRandom run6 = new randomApproach.RunRandom();

			run1.BufferSizeTest("BufferSizeTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", 50, cnt, serviceList,
					packetTypeList, 4);
			run2.BufferSizeTest("BufferSizeTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", "BufferSizeTest/CAR.txt");
			run3.BufferSizeTest("BufferSizeTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NetExpMinDistant.txt");
			run4.BufferSizeTest("BufferSizeTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NetExpMinWeight.txt");
			run5.BufferSizeTest("BufferSizeTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NetExpRandom.txt");
			run6.BufferSizeTest("BufferSizeTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"BufferSizeTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", "BufferSizeTest/Random.txt");

			cnt++;

			run1.RUN();
			run2.RUN();
			run3.RUN();
			run4.RUN();
			run5.RUN();
			run6.RUN();
		}

	}

	public static void NumberOfNodesTest() {

		PrepareResultFiles("NumberOfNodesTest");

		ArrayList<Integer> serviceList = new ArrayList<Integer>();
		ArrayList<Integer> packetTypeList = new ArrayList<Integer>();

		serviceList.add(15);
		packetTypeList.add(300);

		int cnt = 10;
		while (cnt <= 100) {

			store.RunStore run1 = new store.RunStore();
			carApproach.RunCar run2 = new carApproach.RunCar();
			netexpMinDistant.RunNetExp run3 = new netexpMinDistant.RunNetExp();
			netexpMinWeight.RunNetExp run4 = new netexpMinWeight.RunNetExp();
			netexpRandom.RunNetExp run5 = new netexpRandom.RunNetExp();
			randomApproach.RunRandom run6 = new randomApproach.RunRandom();

			packetTypeList.set(0, cnt * 30);

			run1.NumberOfNodesTest("NumberOfNodesTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", cnt, 10, serviceList,
					packetTypeList, 4);
			run2.NumberOfNodesTest("NumberOfNodesTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/CAR.txt");
			run3.NumberOfNodesTest("NumberOfNodesTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NetExpMinDistant.txt");
			run4.NumberOfNodesTest("NumberOfNodesTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NetExpMinWeight.txt");
			run5.NumberOfNodesTest("NumberOfNodesTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NetExpRandom.txt");
			run6.NumberOfNodesTest("NumberOfNodesTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfNodesTest/Random.txt");

			if (cnt == 10)
				cnt = 0;
			cnt += 20;

			run1.RUN();
			run2.RUN();
			run3.RUN();
			run4.RUN();
			run5.RUN();
			run6.RUN();
		}

	}

	public static void NumberOfPacketsTest() {
		PrepareResultFiles("NumberOfPacketsTest");

		ArrayList<Integer> serviceList = new ArrayList<Integer>();
		ArrayList<Integer> packetTypeList = new ArrayList<Integer>();

		serviceList.add(15);
		packetTypeList.add(1000);

		int cnt = 100;
		while (cnt <= 10000) {

			store.RunStore run1 = new store.RunStore();
			carApproach.RunCar run2 = new carApproach.RunCar();
			netexpMinDistant.RunNetExp run3 = new netexpMinDistant.RunNetExp();
			netexpMinWeight.RunNetExp run4 = new netexpMinWeight.RunNetExp();
			netexpRandom.RunNetExp run5 = new netexpRandom.RunNetExp();
			randomApproach.RunRandom run6 = new randomApproach.RunRandom();

			packetTypeList.set(0, cnt);

			run1.NumberOfPacketsTest("NumberOfPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", 50, 10, serviceList,
					packetTypeList, 4);
			run2.NumberOfPacketsTest("NumberOfPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/CAR.txt");
			run3.NumberOfPacketsTest("NumberOfPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NetExpMinDistant.txt");
			run4.NumberOfPacketsTest("NumberOfPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NetExpMinWeight.txt");
			run5.NumberOfPacketsTest("NumberOfPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NetExpRandom.txt");
			run6.NumberOfPacketsTest("NumberOfPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"NumberOfPacketsTest/Random.txt");

			cnt += 100;

			run1.RUN();
			run2.RUN();
			run3.RUN();
			run4.RUN();
			run5.RUN();
			run6.RUN();
		}

	}

	public static void ArrivalRateTest() {
		PrepareResultFiles("ArrivalRateTest");

		ArrayList<Integer> serviceList = new ArrayList<Integer>();
		ArrayList<Integer> packetTypeList = new ArrayList<Integer>();

		serviceList.add(15);
		packetTypeList.add(1000);

		int cnt = 1;
		while (cnt <= 20) {

			store.RunStore run1 = new store.RunStore();
			carApproach.RunCar run2 = new carApproach.RunCar();
			netexpMinDistant.RunNetExp run3 = new netexpMinDistant.RunNetExp();
			netexpMinWeight.RunNetExp run4 = new netexpMinWeight.RunNetExp();
			netexpRandom.RunNetExp run5 = new netexpRandom.RunNetExp();
			randomApproach.RunRandom run6 = new randomApproach.RunRandom();

			run1.ArrivalRateTest("ArrivalRateTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", 50, 10, serviceList,
					packetTypeList, cnt);
			run2.ArrivalRateTest("ArrivalRateTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", "ArrivalRateTest/CAR.txt");
			run3.ArrivalRateTest("ArrivalRateTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NetExpMinDistant.txt");
			run4.ArrivalRateTest("ArrivalRateTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NetExpMinWeight.txt");
			run5.ArrivalRateTest("ArrivalRateTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NetExpRandom.txt");
			run6.ArrivalRateTest("ArrivalRateTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"ArrivalRateTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", "ArrivalRateTest/Random.txt");

			cnt += 1;

			run1.RUN();
			run2.RUN();
			run3.RUN();
			run4.RUN();
			run5.RUN();
			run6.RUN();
		}
	}

	public static void DifferentPacketsTest() {
		PrepareResultFiles("DifferentPacketsTest");

		ArrayList<Integer> serviceList = new ArrayList<Integer>();
		ArrayList<Integer> packetTypeList = new ArrayList<Integer>();

		serviceList.add(3);
		packetTypeList.add(100);

		int cnt = 1;
		while (cnt <= 10) {

			store.RunStore run1 = new store.RunStore();
			carApproach.RunCar run2 = new carApproach.RunCar();
			netexpMinDistant.RunNetExp run3 = new netexpMinDistant.RunNetExp();
			netexpMinWeight.RunNetExp run4 = new netexpMinWeight.RunNetExp();
			netexpRandom.RunNetExp run5 = new netexpRandom.RunNetExp();
			randomApproach.RunRandom run6 = new randomApproach.RunRandom();

			serviceList.add(cnt * 3);
			packetTypeList.add(cnt * 100);

			run1.DifferentPacketsTest("DifferentPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt", 50, 10, serviceList,
					packetTypeList, 2);
			run2.DifferentPacketsTest("DifferentPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/CAR.txt");
			run3.DifferentPacketsTest("DifferentPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NetExpMinDistant.txt");
			run4.DifferentPacketsTest("DifferentPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NetExpMinWeight.txt");
			run5.DifferentPacketsTest("DifferentPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NetExpRandom.txt");
			run6.DifferentPacketsTest("DifferentPacketsTest/NetworkFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/PacketFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/NodesMovementFile" + Integer.toString(cnt) + ".txt",
					"DifferentPacketsTest/Random.txt");

			cnt += 1;

			run1.RUN();
			run2.RUN();
			run3.RUN();
			run4.RUN();
			run5.RUN();
			run6.RUN();
		}
	}

	public static void PrepareResultFiles(String testName) {
		try {
			File file = new File(testName + "/CAR.txt");
			FileWriter fr = new FileWriter(file, true);
			fr.write(FileTitle);
			fr.close();

			file = new File(testName + "/NetExpMinDistant.txt");
			fr = new FileWriter(file, true);
			fr.write(FileTitle);
			fr.close();

			file = new File(testName + "/NetExpMinWeight.txt");
			fr = new FileWriter(file, true);
			fr.write(FileTitle);
			fr.close();

			file = new File(testName + "/NetExpRandom.txt");
			fr = new FileWriter(file, true);
			fr.write(FileTitle);
			fr.close();

			file = new File(testName + "/Random.txt");
			fr = new FileWriter(file, true);
			fr.write(FileTitle);
			fr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
