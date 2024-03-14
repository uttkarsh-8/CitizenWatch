import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_fonts/google_fonts.dart';
// ignore: depend_on_referenced_packages
import 'package:geolocator/geolocator.dart';
// ignore: depend_on_referenced_packages
import 'package:url_launcher/url_launcher.dart';

enum ContentState { page1, page2, page3, page4 }

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int selectedIndex = 0;
  ContentState contentState = ContentState.page1;

  void updateContent(ContentState newState) {
    setState(() {
      contentState = newState;
    });
  }

  final List<Widget> pageList = [
    const Page1(),
    const Page2(),
    const Page3(),
    const Page4(),
  ];

  @override
  Widget build(BuildContext context) {
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersiveSticky);

    return Scaffold(
      body: Column(
        children: [
          Stack(
            children: [
              SizedBox(
                height: 175,
                width: double.infinity,
                child: Image.asset(
                  'assets/images/area_image.jpg', // Replace with your image path
                  fit: BoxFit.fill,
                ),
              ),
              Positioned(
                bottom: 10,
                left: 10,
                child: Text(
                  'Current Area',
                  style: GoogleFonts.metrophobic(
                      color: Colors.white, fontWeight: FontWeight.bold),
                ),
              ),
              Positioned(
                bottom: 10,
                right: 10,
                child: Text(
                  'Person In-charge',
                  style: GoogleFonts.metrophobic(
                      color: Colors.white, fontWeight: FontWeight.bold),
                ),
              ),
              Positioned(
                top: 10.0,
                left: 10.0,
                child: ElevatedButton(
                  onPressed: () {},
                  child: const Text('Left Button'),
                ),
              ),
              Positioned(
                top: 10.0,
                right: 10.0,
                child: ElevatedButton(
                  onPressed: () {},
                  child: const Text('Right Button'),
                ),
              ),
            ],
          ),
          Container(
            padding: const EdgeInsets.all(10.0), // Adjust padding as needed
            decoration: BoxDecoration(
              color: Colors.white, // Set box background color
              border: Border.all(
                  color: Colors.grey, width: 1.0), // Optional: Add border
              borderRadius:
                  BorderRadius.circular(10.0), // Rounded corners for the box
            ),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Expanded(
                      child: TextButton(
                        onPressed: () => updateContent(ContentState.page1),
                        child: const Text('Button 1'),
                      ),
                    ),
                    Expanded(
                      child: TextButton(
                        onPressed: () => updateContent(ContentState.page2),
                        child: const Text('Button 2'),
                      ),
                    ),
                    Expanded(
                      child: TextButton(
                        onPressed: () => updateContent(ContentState.page3),
                        child: const Text('Button 3'),
                      ),
                    ),
                    Expanded(
                      child: TextButton(
                        onPressed: () => updateContent(ContentState.page4),
                        child: const Text('Button 4'),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          IndexedStack(
            // Display content based on contentState
            index: contentState.index,
            children: pageList,
          ),
        ],
      ),
    );
  }
}

class Page1 extends StatelessWidget {
  const Page1({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Content for Page 1'));
  }
}

class Page2 extends StatelessWidget {
  const Page2({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Content for Page 2'));
  }
}

class Page3 extends StatelessWidget {
  const Page3({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        padding: const EdgeInsets.all(10.0),
        decoration: BoxDecoration(
          color: Colors.white, // Set box background color
          border: Border.all(
              color: Colors.grey, width: 1.0), // Optional: Add border
          borderRadius:
              BorderRadius.circular(10.0), // Rounded corners for the box
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            TextButton(
              onPressed: () async {
                final position = await Geolocator.getCurrentPosition(
                  desiredAccuracy: LocationAccuracy.high,
                );

                final latitude = position.latitude;
                final longitude = position.longitude;
                final url =
                    Uri.parse('geo:$latitude,$longitude?q=my+location&z=16');

                if (await canLaunchUrl(url)) {
                  await launchUrl(url);
                } else {
                  // ignore: use_build_context_synchronously
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                      content: Text('Unable to open Google Maps'),
                    ),
                  );
                }
              },
              child: const Text('Button 1'),
            ),
            TextButton(
              onPressed: () {}, // Update with your button action
              child: const Text('Button 2'),
            ),
            TextButton(
              onPressed: () {}, // Update with your button action
              child: const Text('Button 3'),
            ),
          ],
        ),
      ),
    );
  }
}

class Page4 extends StatelessWidget {
  const Page4({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Content for Page 4'));
  }
}
