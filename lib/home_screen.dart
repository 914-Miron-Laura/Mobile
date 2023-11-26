import 'package:flutter/material.dart';
import 'package:inc/db_helper.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  List<Map<String, dynamic>> _allData = [];

  bool _isLoading = true;

  void _refreshData() async {
    final data = await SQlHelper.getAllData();
    setState(() {
      _allData = data;
      _isLoading = false;
    });
  }

  @override
  void initState() {
    super.initState();
    _refreshData();
  }

  Future<void> _updateData(int id) async {
    await SQlHelper.updateData(
        id,
        _nameEditingController.text,
        _categoryEditingController.text,
        _priceEditingController.text,
        _sizeEditingController.text,
        _colorEditingController.text);
    _refreshData();
  }

  Future<void> _deleteData(int id) async {
    await SQlHelper.deleteData(id);
    ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
      backgroundColor: Colors.redAccent,
      content: Text("Data Deleted"),
    ));
    _refreshData();
  }

  Future<void> _addData() async {
    try {
      print("Adding Data...");
      await SQlHelper.createData(
          _nameEditingController.text,
          _categoryEditingController.text,
          _priceEditingController.text,
          _sizeEditingController.text,
          _colorEditingController.text);
      print("Data Added!");
      _refreshData();
    } catch (e) {
      print("Error adding data: $e");
    }
  }

  final TextEditingController _nameEditingController = TextEditingController();
  final TextEditingController _categoryEditingController =
      TextEditingController();
  final TextEditingController _priceEditingController = TextEditingController();
  final TextEditingController _sizeEditingController = TextEditingController();
  final TextEditingController _colorEditingController = TextEditingController();

  void showBottomSheet(int? id) async {
    if (id != null) {
      final existingData =
          _allData.firstWhere((element) => element['id'] == id);
      _nameEditingController.text = existingData['title'];
      _categoryEditingController.text = existingData['category'];
      _priceEditingController.text = existingData['price'];
      _sizeEditingController.text = existingData['size'];
      _colorEditingController.text = existingData['color'];
    }

    showModalBottomSheet(
      elevation: 5,
      isScrollControlled: true,
      context: context,
      builder: (_) => Container(
        padding: EdgeInsets.only(
          top: 30,
          left: 15,
          right: 15,
          bottom: MediaQuery.of(context).viewInsets.bottom + 50,
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.end,
          children: [
            TextField(
              controller: _nameEditingController,
              decoration: const InputDecoration(
                  border: OutlineInputBorder(), hintText: "Name"),
            ),
            const SizedBox(height: 10),
            TextField(
              controller: _categoryEditingController,
              decoration: const InputDecoration(
                  border: OutlineInputBorder(), hintText: "Style"),
            ),
            const SizedBox(height: 10),
            TextField(
              controller: _priceEditingController,
              decoration: const InputDecoration(
                  border: OutlineInputBorder(), hintText: "Price"),
            ),
            const SizedBox(height: 10),
            Center(
              child: ElevatedButton(
                onPressed: () async {
                  if (id == null) {
                    await _addData();
                  }
                  if (id != null) {
                    await _updateData(id);
                  }

                  _nameEditingController.text = "";
                  _categoryEditingController.text = "";
                  _priceEditingController.text = "";

                  Navigator.of(context).pop();
                  print("Data Added");
                },
                child: Padding(
                  padding: const EdgeInsets.all(18),
                  child: Text(
                    id == null ? "Add Data" : "Update",
                    style: const TextStyle(fontSize: 18, fontWeight: FontWeight.w500),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFECEAF4),
      appBar: AppBar(
        title: const Text("CRUD operations"),
      ),
      body: _isLoading
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : ListView.builder(
              itemCount: _allData.length,
              itemBuilder: (context, index) => Card(
                margin: const EdgeInsets.all(15),
                child: ListTile(
                  title: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 5),
                    child: Text(
                      _allData[index]['name'],
                      style: const TextStyle(
                        fontSize: 20,
                      ),
                    ),
                  ),
                  //subtitle: Text(),
                ),
              ),
            ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => showBottomSheet(null),
        child: const Icon(Icons.add),
      ),
    );
  }
}
