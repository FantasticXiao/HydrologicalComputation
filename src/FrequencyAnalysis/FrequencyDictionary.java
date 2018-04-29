package FrequencyAnalysis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author amos
 * @Date 2018-4-20
 * @function get Fai instead of norminv function
 */
public class FrequencyDictionary {
    private static final double FREQUENCY[]={0.01,0.02,0.05,0.1,0.2,0.5,1,2,3,5,10,20,25,30,40,50,60,70,75,80,90,95,97,99,99.5,99.7};
    private static final double CS[]={0,0.02,0.04,0.06,0.08,0.1,0.12,0.14,0.16,0.18,0.2,0.22,0.24,0.26,0.28,0.3,0.32,0.34
            ,0.36,0.38,0.4,0.42,0.44,0.46,0.48,0.5,0.55,0.6,0.65,0.7,0.75,0.8,0.85,0.9,0.95,1,1.05,1.1,1.15,1.2,1.25
            ,1.3,1.35,1.4,1.45,1.5,1.55,1.6,1.65,1.7,1.75,1.8,1.85,1.9,1.95,2,2.1,2.2,2.3,2.4,2.5,2.6,2.7,2.8,2.9
            ,3,3.1,3.2,3.3,3.4,3.5,3.6,3.7,3.8,3.9,4,4.1,4.2,4.3,4.4,4.5,4.6,4.7,4.8,4.9,5};
    private static final double FAI[][] = new double[][]{
            {3.719, 3.54, 3.291, 3.09, 2.878, 2.576, 2.236, 2.054, 1.881, 1.645, 1.282, 0.842, 0.674, 0.524, 0.253, 0, -0.253, -0.524, -0.674, -0.842, -1.282, -1.645, -1.881, -2.236, -2.576, -3.09},
            {3.768, 3.582, 3.325, 3.119, 2.903, 2.595, 2.341, 2.064, 1.889, 1.651, 1.284, 0.841, 0.673, 0.522, 0.25, -0.003, -0.256, -0.52, -0.676, -0.843, -1.279, -1.639, -1.872, -2.312, -2.557, -3.061},
            {3.807, 3.619, 3.357, 3.148, 2.927, 2.613, 2.356, 2.075, 1.898, 1.656, 1.286, 0.84, 0.671, 0.52, 0.247, -0.007, -0.26, -0.529, -0.678, -0.843, -1.277, -1.633, -1.864, -2.297, -2.538, -3.033},
            {3.849, 3.657, 3.389, 3.176, 2.951, 2.632, 2.37, 2.086, 1.906, 1.662, 1.288, 0.839, 0.669, 0.517, 0.244, -0.01, -0.263, -0.532, -0.68, -0.844, -1.275, -1.628, -1.855, -2.282, -2.519, -3.005},
            {3.892, 3.695, 3.422, 3.205, 2.976, 2.651, 2.385, 2.096, 1.914, 1.667, 1.29, 0.838, 0.667, 0.515, 0.241, -0.013, -0.266, -0.534, -0.681, -0.845, -1.273, -1.622, -1.847, -2.267, -2.501, -2.976},
            {3.935, 3.734, 3.455, 3.233, 3, 2.67, 2.4, 2.107, 1.923, 1.673, 1.292, 0.83, 0.665, 0.512, 0.238, -0.016, -0.269, -0.536, -0.683, -0.846, -1.27, -1.616, -1.838, -2.252, -2.482, -2.948},
            {3.978, 3.773, 3.488, 3.262, 3.024, 2.688, 2.414, 2.118, 1.931, 1.678, 1.294, 0.829, 0.663, 0.51, 0.235, -0.02, -0.272, -0.538, -0.685, -0.847, -1.268, -1.61, -1.829, -2.238, -2.463, -2.92},
            {4.022, 3.812, 3.521, 3.291, 3.049, 2.707, 2.429, 2.128, 1.939, 1.684, 1.296, 0.834, 0.661, 0.507, 0.231, -0.023, -0.275, -0.541, -0.687, -0.848, -1.266, -1.604, -1.821, -2.223, -2.444, -2.892},
            {4.066, 3.851, 3.555, 3.319, 3.073, 2.726, 2.443, 2.139, 1.947, 1.689, 1.298, 0.833, 0.659, 0.504, 0.228, -0.027, -0.278, -0.543, -0.688, -0.848, -1.263, -1.598, -1.812, -2.208, -2.425, -2.864},
            {4.109, 3.89, 3.588, 3.348, 3.097, 2.745, 2.458, 2.149, 1.955, 1.694, 1.299, 0.832, 0.657, 0.502, 0.225, -0.03, -0.281, -0.545, -0.69, -0.849, -1.261, -1.592, -1.803, -2.193, -2.407, -2.836},
            {4.153, 3.929, 3.621, 3.377, 3.122, 2.763, 2.472, 2.159, 1.964, 1.7, 1.301, 0.83, 0.655, 0.499, 0.222, -0.033, -0.284, -0.548, -0.691, -0.85, -1.258, -1.586, -1.794, -2.178, -2.388, -2.808},
            {4.197, 3.969, 3.654, 3.406, 3.146, 2.781, 2.487, 2.17, 1.972, 1.705, 1.303, 0.829, 0.653, 0.497, 0.219, -0.037, -0.287, -0.55, -0.693, -0.851, -1.256, -1.58, -1.786, -2.164, -2.369, -2.78},
            {4.241, 4.008, 3.688, 3.435, 3.17, 2.8, 2.501, 2.18, 1.98, 1.71, 1.305, 0.828, 0.651, 0.494, 2.215, -0.04, -0.29, -0.552, -0.695, -0.851, -1.253, -1.574, -1.777, -2.149, -2.35, -2.752},
            {4.285, 4.048, 3.721, 3.464, 3.195, 2.819, 2.516, 2.19, 1.988, 1.715, 1.306, 0.826, 0.649, 0.491, 0.212, -0.043, -0.293, -0.554, -0.696, -0.852, -1.25, -1.568, -1.768, -2.134, -2.332, -2.724},
            {4.33, 4.087, 3.755, 3.492, 3.219, 2.838, 2.53, 2.201, 1.996, 1.721, 1.308, 0.825, 0.647, 0.489, 0.209, -0.046, -0.296, -0.556, -0.697, -0.852, -1.248, -1.561, -1.759, -2.119, -2.313, -2.697},
            {4.374, 4.127, 3.788, 3.521, 3.244, 2.856, 2.544, 2.211, 2.003, 1.726, 1.309, 0.824, 0.644, 0.486, 0.206, -0.05, -0.299, -0.558, -0.699, -0.853, -1.245, -1.555, -1.75, -2.104, -2.294, -2.667},
            {4.418, 4.167, 3.822, 3.55, 3.268, 2.875, 2.559, 2.221, 2.011, 1.731, 1.311, 0.822, 0.642, 0.483, 0.202, -0.053, -0.302, -0.565, -0.7, -0.853, -1.242, -1.549, -1.741, -2.089, -2.276, -2.642},
            {4.468, 4.206, 3.855, 3.579, 3.293, 2.894, 2.573, 2.231, 2.019, 1.736, 1.312, 0.821, 0.64, 0.481, 0.199, -0.056, -0.305, -0.563, -0.702, -0.854, -1.24, -1.543, -1.732, -2.074, -2.257, -2.614},
            {4.508, 4.246, 3.889, 3.608, 3.317, 2.912, 2.587, 2.241, 2.027, 1.741, 1.314, 0.819, 0.638, 0.478, 0.196, -0.06, -0.308, -0.565, -0.703, -0.854, -1.237, -1.536, -1.723, -2.059, -2.238, -2.587},
            {4.552, 4.286, 3.922, 3.637, 3.341, 2.931, 2.601, 2.251, 2.035, 1.746, 1.315, 0.818, 0.636, 0.475, 0.192, -0.063, -0.311, -0.567, -0.705, -0.855, -1.234, -1.53, -1.714, -2.044, -2.22, -2.56},
            {4.597, 4.326, 3.956, 3.666, 3.366, 2.949, 2.615, 2.261, 2.042, 1.751, 1.317, 0.816, 0.633, 0.472, 0.189, -0.066, -0.314, -0.569, -0.706, -0.855, -1.231, -1.524, -1.705, -2.029, -2.201, -2.533},
            {4.642, 4.366, 3.99, 3.695, 3.39, 2.967, 2.63, 2.271, 2.05, 1.755, 1.318, 0.815, 0.631, 0.469, 0.186, -0.07, -0.316, -0.571, -0.707, -0.855, -1.228, -1.517, -1.696, -2.014, -2.182, -2.506},
            {4.687, 4.406, 4.023, 3.724, 3.414, 2.986, 2.644, 2.281, 2.058, 1.76, 1.319, 0.813, 0.629, 0.467, 0.183, -0.073, -0.319, -0.573, -0.708, -0.856, -1.225, -1.511, -1.687, -2, -2.164, -2.479},
            {4.731, 4.446, 4.057, 3.753, 3.439, 3.004, 2.658, 2.291, 2.065, 1.765, 1.321, 0.811, 0.626, 0.464, 0.179, -0.076, -0.322, -0.575, -0.709, -0.856, -1.222, -1.504, -1.677, -1.985, -2.145, -2.452},
            {4.776, 4.486, 4.001, 3.782, 3.463, 3.023, 2.672, 2.301, 2.073, 1.77, 1.322, 0.81, 0.624, 0.461, 0.176, -0.08, -0.325, -0.576, -0.711, -0.856, -1.219, -1.498, -1.668, -1.97, -2.127, -2.425},
            {4.821, 4.526, 4.124, 3.811, 3.487, 3.041, 2.686, 2.311, 2.08, 1.774, 1.323, 0.808, 0.622, 0.458, 0.173, -0.083, -0.328, -0.578, -0.712, -0.857, -1.216, -1.491, -1.659, -1.955, -2.108, -2.399},
            {4.934, 4.626, 4.209, 3.883, 3.548, 3.087, 2.721, 2.335, 2.099, 1.786, 1.326, 0.804, 0.616, 0.451, 0.164, -0.091, -0.335, -0.583, -0.715, -0.857, -1.208, -1.474, -1.636, -1.917, -2.062, -2.333},
            {5.047, 4.727, 4.293, 3.956, 3.609, 3.132, 2.755, 2.359, 2.117, 1.797, 1.329, 0.799, 0.609, 0.444, 0.156, -0.099, -0.342, -0.588, -0.718, -0.857, -1.2, -1.458, -1.613, -1.88, -2.016, -2.268},
            {5.16, 4.828, 4.377, 4.028, 3.669, 3.178, 2.79, 2.383, 2.135, 1.808, 1.331, 0.795, 0.603, 0.436, 0.148, -0.108, -0.349, -0.592, -0.72, -0.857, -1.192, -1.441, -1.589, -1.843, -1.971, -2.204},
            {5.274, 4.928, 4.462, 4.1, 3.73, 3.223, 2.824, 2.407, 2.153, 1.819, 1.333, 0.79, 0.596, 0.429, 0.139, -0.116, -0.356, -0.596, -0.722, -0.857, -1.183, -1.423, -1.566, -1.806, -1.926, -2.141},
            {5.388, 5.029, 4.546, 4.172, 3.79, 3.268, 2.857, 2.43, 2.17, 1.829, 1.335, 0.785, 0.59, 0.421, 0.131, -0.124, -0.362, -0.6, -0.724, -0.857, -1.175, -1.406, -1.542, -1.769, -1.881, -2.078},
            {5.501, 5.13, 4.631, 4.244, 3.85, 3.312, 2.891, 2.453, 2.187, 1.839, 1.336, 0.78, 0.583, 0.413, 0.122, -0.132, -0.369, -0.604, -0.726, -0.856, -1.166, -1.389, -1.518, -1.733, -1.837, -2.017},
            {5.615, 5.231, 4.715, 4.316, 3.91, 3.357, 2.924, 2.476, 2.204, 1.849, 1.338, 0.775, 0.576, 0.405, 0.113, -0.14, -0.375, -0.608, -0.728, -0.855, -1.157, -1.371, -1.494, -1.696, -1.793, -1.958},
            {5.729, 5.332, 4.799, 4.388, 3.969, 3.401, 2.957, 2.498, 2.22, 1.859, 1.339, 0.769, 0.569, 0.397, 0.105, -0.148, -0.382, -0.611, -0.73, -0.854, -1.147, -1.353, -1.47, -1.66, -1.749, -1.899},
            {5.843, 5.443, 4.883, 4.46, 4.029, 3.445, 2.99, 2.52, 2.237, 1.868, 1.34, 0.763, 0.562, 0.389, 0.096, -0.156, -0.388, -0.615, -0.731, -0.853, -1.137, -1.335, -1.446, -1.624, -1.706, -1.842},
            {5.957, 5.534, 4.967, 4.531, 4.088, 3.489, 3.023, 2.542, 2.253, 1.877, 1.34, 0.757, 0.555, 0.381, 0.088, -0.164, -0.394, -0.618, -0.732, -0.852, -1.128, -1.317, -1.422, -1.588, -1.664, -1.786},
            {6.071, 5.635, 5.051, 4.602, 4.147, 3.532, 3.055, 2.564, 2.268, 1.886, 1.341, 0.752, 0.547, 0.373, 0.079, -0.172, -0.4, -0.621, -0.733, -0.85, -1.118, -1.299, -1.398, -1.553, -1.622, -1.731},
            {6.185, 5.736, 5.134, 4.674, 4.206, 3.575, 3.087, 2.585, 2.284, 1.894, 1.341, 0.745, 0.54, 0.365, 0.07, -0.18, -0.406, -0.624, -0.734, -0.848, -1.107, -1.28, -1.374, -1.518, -1.581, -1.678},
            {6.299, 5.836, 5.218, 4.774, 4.264, 3.618, 3.118, 2.606, 2.299, 1.902, 1.341, 0.739, 0.532, 0.356, 0.062, -0.187, -0.412, -0.627, -0.735, -0.846, -1.097, -1.262, -1.35, -1.484, -1.541, -1.627},
            {6.413, 5.937, 5.301, 4.815, 4.323, 3.661, 3.149, 2.626, 2.313, 1.91, 1.341, 0.733, 0.524, 0.348, 0.053, -0.195, -0.418, -0.629, -0.735, -0.844, -1.086, -1.243, -1.327, -1.449, -1.501, -1.577},
            {6.526, 6.037, 5.384, 4.885, 4.381, 3.703, 3.18, 2.647, 2.328, 1.917, 1.34, 0.726, 0.517, 0.339, 0.044, -0.203, -0.424, -0.632, -0.735, -0.841, -1.075, -1.224, -1.303, -1.416, -1.462, -1.529},
            {6.64, 6.137, 5.467, 4.955, 4.438, 3.745, 3.211, 2.667, 2.342, 1.925, 1.339, 0.719, 0.508, 0.331, 0.036, -0.21, -0.429, -0.634, -0.735, -0.838, -1.064, -1.206, -1.279, -1.383, -1.424, -1.482},
            {6.753, 6.237, 5.55, 5.025, 4.496, 3.787, 3.241, 2.686, 2.356, 1.932, 1.338, 0.712, 0.5, 0.322, 0.027, -0.218, -0.434, -0.636, -0.735, -0.835, -1.053, -1.187, -1.255, -1.35, -1.387, -1.437},
            {6.867, 6.337, 5.632, 5.095, 4.553, 3.828, 3.271, 2.706, 2.369, 1.938, 1.337, 0.705, 0.492, 0.318, 0.018, -0.225, -0.44, -0.638, -0.735, -0.832, -1.041, -1.168, -1.232, -1.318, -1.351, -1.394},
            {6.98, 6.437, 5.715, 5.164, 4.61, 3.869, 3.301, 2.725, 2.382, 1.945, 1.335, 0.698, 0.484, 0.304, 0.01, -0.233, -0.445, -0.639, -0.734, -0.829, -1.03, -1.15, -1.208, -1.287, -1.316, -1.353},
            {7.093, 6.536, 5.797, 5.234, 4.666, 3.91, 3.33, 2.743, 2.395, 1.95, 1.333, 0.691, 0.475, 0.295, 0.001, -0.24, -0.449, -0.641, -0.733, -0.825, -1.018, -1.131, -1.185, -1.256, -1.282, -1.313},
            {7.206, 6.636, 5.878, 5.302, 4.723, 3.95, 3.359, 2.762, 2.4, 1.957, 1.331, 0.683, 0.467, 0.286, -0.008, -0.247, -0.454, -0.642, -0.732, -0.821, -1.006, -1.112, -1.162, -1.226, -1.248, -1.275},
            {7.318, 6.735, 5.96, 5.371, 4.779, 3.99, 3.388, 2.78, 2.42, 1.962, 1.329, 0.675, 0.458, 0.277, -0.016, -0.254, -0.459, -0.643, -0.731, -0.817, -0.994, -1.093, -1.14, -1.197, -1.216, -1.283},
            {7.43, 6.833, 6.041, 5.439, 4.834, 4.03, 3.41, 2.797, 2.432, 1.967, 1.326, 0.667, 0.45, 0.268, -0.025, -0.261, -0.463, -0.644, -0.729, -0.813, -0.982, -1.075, -1.117, -1.168, -1.185, -1.203},
            {7.543, 6.932, 6.122, 5.507, 4.89, 4.069, 3.444, 2.815, 2.444, 1.972, 1.324, 0.66, 0.441, 0.259, -0.033, -0.268, -0.467, -0.644, -0.727, -0.808, -0.97, -1.056, -1.095, -1.14, -1.155, -1.17},
            {7.655, 7.03, 6.203, 5.575, 4.945, 4.108, 3.472, 2.832, 2.455, 1.977, 1.321, 0.652, 0.432, 0.25, -0.042, -0.275, -0.472, -0.645, -0.725, -0.804, -0.957, -1.038, -1.073, -1.113, -1.126, -1.138},
            {7.766, 7.128, 6.283, 5.642, 4.999, 4.147, 3.499, 2.848, 2.466, 1.981, 1.318, 0.643, 0.423, 0.241, -0.05, -0.281, -0.475, -0.645, -0.723, -0.799, -0.945, -1.02, -1.052, -1.087, -1.097, -1.107},
            {7.878, 7.226, 6.363, 5.709, 5.054, 4.185, 3.526, 2.865, 2.477, 1.985, 1.314, 0.635, 0.414, 0.232, -0.059, -0.288, -0.479, -0.645, -0.721, -0.794, -0.932, -1.002, -1.031, -1.062, -1.07, -1.078},
            {7.989, 7.323, 6.443, 5.775, 5.108, 4.223, 3.553, 2.881, 2.487, 1.989, 1.311, 0.627, 0.405, 0.222, -0.067, -0.294, -0.483, -0.645, -0.718, -0.788, -0.92, -0.984, -1.01, -1.037, -1.044, -1.051},
            {8.1, 7.42, 6.522, 5.842, 5.181, 4.261, 3.579, 2.897, 2.497, 1.993, 1.307, 0.618, 0.396, 0.213, -0.076, -0.301, -0.486, -0.644, -0.715, -0.783, -0.907, -0.966, -0.989, -1.013, -1.019, -1.024},
            {8.21, 7.517, 6.601, 5.908, 5.215, 4.298, 3.605, 2.912, 2.507, 1.996, 1.303, 0.605, 0.386, 0.204, -0.084, -0.307, -0.489, -0.643, -0.712, -0.777, -0.895, -0.949, -0.97, -0.99, -0.995, -0.999},
            {8.431, 7.71, 6.758, 6.039, 5.22, 4.372, 3.656, 2.942, 2.525, 2.001, 1.294, 0.592, 0.368, 0.185, -0.1, -0.319, -0.495, -0.641, -0.706, -0.765, -0.869, -0.915, -0.931, -0.946, -0.949, -0.952},
            {8.65, 7.901, 6.914, 6.108, 5.424, 4.444, 3.705, 2.97, 2.542, 2.006, 1.284, 0.574, 0.349, 0.167, -0.116, -0.33, -0.5, -0.638, -0.698, -0.752, -0.844, -0.882, -0.894, -0.905, -0.907, -0.9509},
            {8.868, 8.091, 7.068, 6.296, 5.527, 4.515, 3.753, 2.997, 2.558, 2.009, 1.274, 0.556, 0.33, 0.148, -0.131, -0.341, -0.504, -0.634, -0.69, -0.739, -0.819, -0.85, -0.86, -0.867, -0.868, -0.869},
            {9.084, 8.28, 7.221, 6.423, 5.628, 4.584, 3.8, 3.023, 2.573, 2.011, 1.262, 0.537, 0.311, 0.13, -0.147, -0.351, -0.507, -0.63, -0.681, -0.725, -0.795, -0.819, -0.827, -0.832, -0.833, -0.833},
            {9.299, 8.468, 7.373, 6.548, 5.728, 4.652, 3.845, 3.048, 2.587, 2.012, 1.25, 0.518, 0.292, 0.111, -0.161, -0.36, -0.51, -0.625, -0.671, -0.711, -0.771, -0.79, -0.796, -0.799, -0.8, -0.8},
            {9.513, 8.654, 7.523, 6.672, 5.826, 4.718, 3.889, 3.071, 2.599, 2.013, 1.238, 0.499, 0.272, 0.093, -0.176, -0.369, -0.512, -0.619, -0.661, -0.696, -0.747, -0.762, -0.766, -0.769, -0.769, -0.769},
            {9.725, 8.838, 7.671, 6.794, 5.923, 4.783, 3.932, 3.093, 2.61, 2.012, 1.224, 0.479, 0.253, 0.075, -0.189, -0.376, -0.513, -0.612, -0.65, -0.681, -0.724, -0.736, -0.739, -0.74, -0.741, -0.741},
            {9.936, 9.021, 7.818, 6.915, 6.019, 4.847, 3.973, 3.114, 2.62, 2.01, 1.21, 0.46, 0.234, 0.057, -0.203, -0.383, -0.513, -0.604, -0.639, -0.666, -0.702, -0.711, -0.713, -0.714, -0.714, -0.714},
            {10.15, 9.203, 7.964, 7.034, 6.113, 4.908, 4.013, 3.134, 2.629, 2.007, 1.195, 0.44, 0.215, 0.04, -0.215, -0.39, -0.512, -0.596, -0.627, -0.651, -0.681, -0.688, -0.689, -0.69, -0.69, -0.69},
            {10.35, 9.383, 8.108, 7.152, 6.205, 4.97, 4.051, 3.152, 2.637, 2.003, 1.18, 0.42, 0.196, 0.023, -0.227, -0.395, -0.511, -0.588, -0.615, -0.636, -0.66, -0.665, -0.666, -0.667, -0.667, -0.667},
            {10.56, 9.562, 8.251, 7.269, 6.297, 5.029, 4.088, 3.169, 2.644, 1.999, 1.164, 0.401, 0.177, 0.006, -0.239, -0.4, -0.509, -0.579, -0.603, -0.621, -0.6406, -0.6443, -0.6449, -0.6451, -0.6452, -0.6452},
            {10.77, 9.739, 8.393, 7.384, 6.385, 5.087, 4.125, 3.185, 2.649, 1.993, 1.148, 0.381, 0.159, -0.0105, -0.249, -0.405, -0.506, -0.57, -0.591, -0.606, -0.622, -0.6244, -0.6248, -0.625, -0.625, -0.625},
            {10.97, 9.915, 8.532, 7.497, 6.474, 5.144, 4.159, 3.2, 2.654, 1.987, 1.131, 0.361, 0.14, -0.0265, -0.26, -0.408, -0.502, -0.56, -0.578, -0.591, -0.604, -0.6057, -0.606, -0.6061, -0.6061, -0.6061},
            {11.17, 10.09, 8.671, 7.609, 6.561, 5.199, 4.193, 3.214, 2.658, 1.98, 1.113, 0.341, 0.122, -0.0421, -0.269, -0.411, -0.496, -0.55, -0.566, -0.577, -0.587, -0.588, -0.5882, -0.5882, -0.5882, -0.5882},
            {11.37, 10.26, 8.808, 7.72, 6.646, 5.253, 4.225, 3.227, 2.66, 1.972, 1.096, 0.322, 0.105, -0.0573, -0.278, -0.413, -0.494, -0.54, -0.554, -0.562, -0.57, -0.5713, -0.5714, -0.5714, -0.5714, -0.5714},
            {11.57, 10.43, 8.943, 7.829, 6.73, 5.306, 4.256, 3.238, 2.662, 1.963, 1.077, 0.302, 0.0872, -0.0719, -0.286, -0.414, -0.489, -0.53, -0.541, -0.549, -0.5548, -0.5555, -0.5555, -0.5556, -0.5556, -0.5556},
            {11.77, 10.6, 9.077, 7.937, 6.813, 5.307, 4.285, 3.249, 2.663, 1.953, 1.059, 0.283, 0.07, -0.086, -0.293, -0.414, -0.483, -0.52, -0.529, -0.535, -0.5401, -0.5405, -0.5405, -0.5405, -0.5405, -0.5405},
            {11.97, 10.77, 9.21, 8.044, 6.894, 5.407, 4.314, 3.258, 2.663, 1.943, 1.04, 0.264, 0.0535, -0.0997, -0.3, -0.414, -0.478, -0.509, -0.518, -0.522, -0.526, -0.5263, -0.5263, -0.5263, -0.5263, -0.5263},
            {12.16, 10.94, 9.342, 8.149, 6.974, 5.456, 4.342, 3.267, 2.662, 1.932, 1.02, 0.245, 0.0372, -0.113, -0.306, -0.414, -0.471, -0.499, -0.506, -0.51, -0.5126, -0.5128, -0.5128, -0.5128, -0.5128, -0.5128},
            {12.36, 11.11, 9.471, 8.253, 7.053, 5.504, 4.368, 3.274, 2.659, 1.92, 1.001, 0.226, 0.0212, -0.125, -0.312, -0.413, -0.465, -0.489, -0.495, -0.498, -0.4999, -0.5, -0.5, -0.5, -0.5, -0.5},
            {12.55, 11.27, 9.6, 8.355, 7.13, 5.55, 4.393, 3.281, 2.657, 1.908, 0.981, 0.208, 0.0058, -0.137, -0.316, -0.411, -0.458, -0.479, -0.484, -0.486, -0.4877, -0.4878, -0.4878, -0.4878, -0.4878, -0.4878},
            {12.74, 11.48, 9.727, 8.457, 7.206, 5.595, 4.417, 3.286, 2.653, 1.895, 0.961, 0.19, -0.0091, -0.149, -0.32, -0.409, -0.451, -0.469, -0.473, -0.475, -0.4761, -0.4762, -0.4762, -0.4762, -0.4762, -0.4762},
            {12.93, 11.6, 9.853, 8.55, 7.281, 5.639, 4.44, 3.291, 2.649, 1.882, 0.941, 0.172, -0.0236, -0.159, -0.324, -0.406, -0.444, -0.46, -0.463, -0.4643, -0.4651, -0.4651, -0.4651, -0.4651, -0.4651, -0.4651},
            {13.12, 11.76, 9.978, 8.655, 7.355, 5.682, 4.462, 3.295, 2.644, 1.867, 0.92, 0.154, -0.0375, -0.17, -0.327, -0.403, -0.437, -0.45, -0.453, -0.4539, -0.4545, -0.4545, -0.4545, -0.4545, -0.4545, -0.4545},
            {13.3, 11.01, 10.1, 8.752, 7.427, 5.724, 4.483, 3.298, 2.638, 1.853, 0.9, 0.137, -0.051, -0.179, -0.329, -0.4, -0.43, -0.441, -0.443, -0.444, -0.4444, -0.4444, -0.4444, -0.4444, -0.4444, -0.4444},
            {13.49, 12.07, 10.22, 8.848, 7.498, 5.764, 4.503, 3.3, 2.631, 1.838, 0.879, 0.121, -0.0639, -0.188, -0.331, -0.396, -0.423, -0.432, -0.4338, -0.4345, -0.4348, -0.4348, -0.4348, -0.4348, -0.4348, -0.4348},
            {13.68, 12.23, 10.34, 8.943, 7.568, 5.804, 4.522, 3.301, 2.624, 1.822, 0.858, 0.104, -0.0764, -0.197, -0.332, -0.392, -0.416, -0.4236, -0.4248, -0.4253, -0.4255, -0.4255, -0.4255, -0.4255, -0.4255, -0.4255},
            {13.86, 12.38, 10.46, 9.036, 7.637, 5.843, 4.54, 3.301, 2.616, 1.806, 0.837, 0.0884, -0.0881, -0.204, -0.333, -0.388, -0.409, -0.415, -0.4161, -0.4165, -0.4167, -0.4167, -0.4167, -0.4167, -0.4167, -0.4167},
            {13.04, 12.54, 10.58, 9.128, 7.705, 5.88, 4.557, 3.301, 2.608, 1.79, 0.816, 0.0731, -0.0995, -0.212, -0.333, -0.384, -0.402, -0.407, -0.4078, -0.4081, -0.4082, -0.4082, -0.4082, -0.4082, -0.4082, -0.4082},
            {14.22, 12.69, 10.7, 9.22, 7.771, 5.917, 4.573, 3.3, 2.598, 1.773, 0.795, 0.0579, -0.11, -0.218, -0.333, -0.379, -0.395, -0.3991, -0.3997, -0.3999, -0.4, -0.4, -0.4, -0.4, -0.4, -0.4}
    };
    /**
     * get default frequency list
     * @return frequency list
     */
    protected static List<Double> getPlotFrequencies(){
        List<Double> temp= new ArrayList<>();
        for (Double frequency:FREQUENCY){
            temp.add(frequency);
        }
        return temp;
    }
    /**
     *list value of certain frequencies
     * @param frequencies series need to be get
     * @param ex mean value
     * @param cv coefficient of variation
     * @param cs coefficient of skew
     * @return value list
     */
    protected static List<Double> listSimulatePoints(List<Double> frequencies,double ex,double cs,double cv){
        List<Double> tmp=new ArrayList<>();
        for(Double frequency:frequencies){
            tmp.add(getValueByFrequency(ex,cv,cs,frequency));
        }
        return tmp;
    }
    /**
     *get value by frequency
     * @param ex mean value
     * @param cv coefficient of variation
     * @param cs coefficient of skew
     * @param frequency 0~1 frequency need to be checked %
     * @return value of needed frequency
     */
    protected static double getValueByFrequency(double ex,double cv,double cs,double frequency){
        if(frequency<0.01||frequency>=99.7){
            return 0;
        }
        double[] fai=getFaiListByCs(cs);
        int temp=0;
        for (int i=0;i<25;i++) {
            if (FREQUENCY[i] <= frequency && FREQUENCY[i+1] >= frequency) {
                temp = i;
                break;
            }
        }
        double faiTemp=fai[temp+1]-(FREQUENCY[temp+1]-frequency)*(fai[temp+1]-fai[temp]) /(FREQUENCY[temp+1]-FREQUENCY[temp]);
        return new BigDecimal(ex*(cv*faiTemp+1))
                .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *get frequency with specific value under certain parameter
     * @param ex mean value
     * @param cv coefficient of variation
     * @param cs coefficient of skew
     * @param value value need to be checked
     * @return frequency between 0 and 1  %
     */
    protected static double getFrequencyByValue(double ex,double cv,double cs,double value){
        double[] fai=getFaiListByCs(cs);
        double faiTemp=(value/ex-1)/cv;
        int temp=0;
        for (int i=0;i<25;i++) {
            if (faiTemp>=fai[i+1]&&faiTemp<=fai[i]) {
                temp = i;
                break;
            }
        }
        return  new BigDecimal(FREQUENCY[temp+1]-(FREQUENCY[temp+1]-FREQUENCY[temp]) *(fai[temp+1]-faiTemp)/
                (fai[temp+1]-fai[temp])).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * get fai series with specific value
     * @param cs coefficient of skew
     * @return fai value list
     */
    private static double[] getFaiListByCs(double cs){
        double[] fai=new double[26];
        int temp=0;
        for (int j = 0; j <85 ; j++) {
            if(cs>=CS[j]&&cs<=CS[j+1]){
                temp=j;
                break;
            }
        }
        for (int i = 0; i < 26; i++) {
            if (cs==CS[temp]){
                fai[i]=FAI[temp][i];
            }else if (cs==CS[temp+1]){
                fai[i]=FAI[temp+1][i];
            }else{
                fai[i]=new BigDecimal(FAI[temp+1][i]+(FAI[temp][i]-FAI[temp+1][i])*
                        (CS[temp+1]-cs)/(CS[temp+1]-CS[temp]))
                        .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
        return fai;
    }
}
