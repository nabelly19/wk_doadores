import 'dart:io';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'package:wk_doadores/strings.dart';

late final String apiUrl;

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await dotenv.load();
  await Strings.load();
  apiUrl = dotenv.env['API_URL'] ?? "http://localhost:8080/api/candidates";
  runApp(MaterialApp(home: _InitializeApp()));
}

class _InitializeApp extends StatefulWidget {
  const _InitializeApp();

  @override
  _InitializeAppState createState() => _InitializeAppState();
}

class _InitializeAppState extends State<_InitializeApp> {
  late Future<bool> _apiAvailable;

  @override
  void initState() {
    super.initState();
    _apiAvailable = checkAPI();
  }

  Future<bool> checkAPI() async {
    try {
      final response = await http.get(Uri.parse(apiUrl)).timeout(const Duration(seconds: 3));
      return response.statusCode == 200;
    } catch (e) {
      return false;
    }
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<bool>(
      future: _apiAvailable,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return _buildLoadingScreen();
        } else if (snapshot.hasError || snapshot.data == false) {
          return _buildErrorScreen();
        } else {
          return const DonorsApp();
        }
      },
    );
  }

  Widget _buildLoadingScreen() {
    return Scaffold(
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            Strings.get('connecting'),
            textAlign: TextAlign.center,
            style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
          ),
          const SizedBox(height: 30),
          const Center(child: CircularProgressIndicator()),
        ],
      ),
    );
  }

  Widget _buildErrorScreen() {
    return Scaffold(
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Icon(Icons.cloud_off, size: 50, color: Colors.red),
              const SizedBox(height: 10),
              Text(
                Strings.get("unavailable"),
                textAlign: TextAlign.center,
                style: const TextStyle(fontSize: 16),
              ),
              const SizedBox(height: 10),
              Text(
                apiUrl,
                textAlign: TextAlign.center,
                style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 14),
              ),
              const SizedBox(height: 10),
              ElevatedButton(
                onPressed: () {
                  setState(() {
                    _apiAvailable = checkAPI();
                  });
                },
                child: Text(Strings.get("retry")),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class DonorsApp extends StatelessWidget {
  const DonorsApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: Strings.get("title"),
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0x00280404)),
        useMaterial3: true,
      ),
      home: HomePage(title: Strings.get("report")),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.title});

  final String title;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  FilePickerResult? result;
  File? file;

  Map<String, dynamic> donorsByState = {};
  Map<String, dynamic> bmiByAgeGroup = {};
  Map<String, dynamic> obesityPercentage = {};
  Map<String, dynamic> averageAgeByBloodType = {};
  Map<String, dynamic> donorsPerRecipient = {};

  @override
  void initState() {
    super.initState();
    fetchAll();
  }

  Future<void> fetchAll() async{
    fetchData(Strings.get("api_count-by-state"), updateDonorsByState);
    fetchData(Strings.get("api_average-bmi-by-age-group"), updateBmiByAgeGroup);
    fetchData(Strings.get("api_obesity-percentage"), updateObesityPercentage);
    fetchData(Strings.get("api_average-age-by-blood-type"), updateAverageAgeByBloodType);
    fetchData(Strings.get("api_donors-per-recipient"), updateDonorsPerRecipient);
  }

  Future<void> fetchData(String endpoint, Function updateState) async {
    try {
      final http.Response response = await http.get(
        Uri.parse("$apiUrl/$endpoint"),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        final String utf8Body = utf8.decode(response.bodyBytes);
        final Map<String, dynamic> jsonData = json.decode(utf8Body);
        updateState(jsonData);
      } else {
        updateState({"Error": "Status ${response.statusCode}"});
      }
    } catch (e) {
      updateState({"Error": "Connection failed"});
    }
  }

  void updateDonorsByState(Map<String, dynamic> data) {
    setState(() {
      donorsByState = data;
    });
  }

  void updateBmiByAgeGroup(Map<String, dynamic> data) {
    setState(() {
      bmiByAgeGroup = data;
    });
  }

  void updateObesityPercentage(Map<String, dynamic> data) {
    setState(() {
      obesityPercentage = data;
    });
  }

  void updateAverageAgeByBloodType(Map<String, dynamic> data) {
    setState(() {
      averageAgeByBloodType = data;
    });
  }

  void updateDonorsPerRecipient(Map<String, dynamic> data) {
    setState(() {
      donorsPerRecipient = data;
    });
  }

  Future<void> sendDataToAPI(String? response) async {
    try {
      final http.Response apiResponse = await http.post(
        Uri.parse("$apiUrl/${Strings.get('api_load_data')}"),
        headers: {'Content-Type': 'application/json'},
        body: response,
      );

      if (apiResponse.statusCode == 200 || apiResponse.statusCode == 201) {
        debugPrint("Data successfully sent!");
        fetchAll();
      } else {
        debugPrint("Error sending data. Status: ${apiResponse.statusCode}");
      }
    } catch (e) {
      debugPrint("Error sending data to API: $e");
    }
  }

  Future<void> handleFileSelection() async {
    result = await FilePicker.platform.pickFiles();
    if (result != null) {
      file = File(result?.files.single.path ?? '');
      final String? response = await file?.readAsString();
      sendDataToAPI(response);
    } else {
      debugPrint("No file selected!");
    }
  }

  Widget buildCollapsibleSection(String title, Map<String, dynamic> data) {
    return ExpansionTile(
      title: Text(
        title,
        style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
      ),
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
          child: data.isEmpty
              ? Text(Strings.get("no_data"),
            style: TextStyle(fontSize: 14, fontWeight: FontWeight.w500),
          )
              : Table(
            border: TableBorder.all(color: Colors.grey),
            columnWidths: const {
              0: FlexColumnWidth(2),
              1: FlexColumnWidth(1),
            },
            children: data.entries.map(
                  (entry) => TableRow(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(entry.key),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(convertToInt(entry.value)),
                  ),
                ],
              ),
            ).toList(),
          ),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: donorsByState.isEmpty ?
        Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              const SizedBox(height: 30),
              ElevatedButton(onPressed: handleFileSelection, child:
                  Text(Strings.get("load_json"))
              ),
            ],
          ),
        ) :
        Center(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              buildCollapsibleSection(Strings.get("donorsByState"), donorsByState),
              buildCollapsibleSection(Strings.get("bmiByAgeGroup"), bmiByAgeGroup),
              buildCollapsibleSection(Strings.get("obesityPercentage"), obesityPercentage),
              buildCollapsibleSection(Strings.get("averageAgeByBloodType"), averageAgeByBloodType),
              buildCollapsibleSection(Strings.get("donorsPerRecipient"), donorsPerRecipient),
            ],
          ),
        ),
      ),
    );
  }

  String convertToInt(dynamic value) {
    if (value is double) {
      return value.toInt().toString();
    }
    return value.toString();
  }

}
